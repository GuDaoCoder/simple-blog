package com.blog.biz.file;

import com.blog.biz.enums.AttachmentModule;
import com.blog.biz.enums.StoragePolicy;
import com.blog.biz.model.config.LocalStoragePolicyConfig;
import com.blog.biz.model.entity.AttachmentEntity;
import com.blog.biz.repository.AttachmentRepository;
import com.blog.biz.service.ConfigService;
import com.blog.common.exception.BusinessException;
import com.blog.common.exception.DataNotFoundException;
import com.blog.common.util.FileUtil;
import com.blog.common.util.IpUtil;
import com.blog.common.util.UIdUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * @author Gudao
 * @since 2024/8/16
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class LocalFileHandler implements FileHandler {

	private final ConfigService configService;

	private final AttachmentRepository attachmentRepository;

	private final Environment env;

	@Override
	public boolean support(StoragePolicy storagePolicy) {
		return StoragePolicy.LOCAL.equals(storagePolicy);
	}

	@SneakyThrows
	@Override
	public AttachmentEntity upload(File file, AttachmentModule module) {
		Assert.notNull(file, "The upload file cannot be null");
		// 文件绝对路径MD5加密作为存储名称
		String storageName = DigestUtils.md5Hex(file.getAbsolutePath());
		// 文件hash值
		String parsedFileHash = DigestUtils.md5Hex(Files.readAllBytes(file.toPath()));

		Optional<AttachmentEntity> attachmentOpt = attachmentRepository.findByStorageName(storageName);

		// 文件未上传或文件内容发生变更，需要上传
		if (attachmentOpt.isEmpty() || !StringUtils.equals(parsedFileHash, attachmentOpt.get().getFileHash())) {

			Optional<LocalStoragePolicyConfig> localStoragePolicyConfigOpt = configService.loadLocalStoragePolicy();
			if (localStoragePolicyConfigOpt.isEmpty()) {
				throw new BusinessException("未配置本地存储配置信息");
			}

			String extension = FilenameUtils.getExtension(file.getName());
			// 本地上传后的路径
			String path = module.getLocalPathSupplier().apply(localStoragePolicyConfigOpt.get()) + File.separator
					+ storageName + "." + extension;
			AttachmentEntity attachmentEntity = attachmentOpt.orElseGet(AttachmentEntity::new);
			attachmentEntity.setOriginalName(file.getName())
				.setStorageName(storageName)
				.setOriginalName(FileUtil.getFileNameWithoutExtension(file.getName()))
				.setExtension(extension)
				.setPath(path)
				.setSize(FileUtil.getFileKbSize(file))
				.setStoragePolicy(StoragePolicy.LOCAL)
				.setFileHash(parsedFileHash)
				.setModule(module);
			// 复制文件
			FileUtils.copyFile(file, new File(path));
			// 保存附件信息
			attachmentRepository.save(attachmentEntity);
			return attachmentEntity;
		}

		return attachmentOpt.get();
	}

	@SneakyThrows
	@Override
	public AttachmentEntity upload(MultipartFile file, AttachmentModule module) {
		Optional<LocalStoragePolicyConfig> localStoragePolicyConfigOpt = configService.loadLocalStoragePolicy();
		if (localStoragePolicyConfigOpt.isEmpty()) {
			throw new BusinessException("未配置本地存储配置信息");
		}

		// 文件存储名称
		String storageName = UIdUtil.uid();
		String parsedFileHash = DigestUtils.md5Hex(file.getBytes());
		// 文件扩展名
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		// 本地上传后的路径
		String path = module.getLocalPathSupplier().apply(localStoragePolicyConfigOpt.get()) + File.separator
				+ storageName + "." + extension;

		AttachmentEntity attachmentEntity = new AttachmentEntity();
		attachmentEntity.setOriginalName(file.getName())
			.setStorageName(storageName)
			.setOriginalName(FileUtil.getFileNameWithoutExtension(file.getOriginalFilename()))
			.setExtension(extension)
			.setPath(path)
			.setSize(FileUtil.getFileKbSize(file))
			.setStoragePolicy(StoragePolicy.LOCAL)
			.setFileHash(parsedFileHash)
			.setModule(module);
		Path uploadPath = Paths.get(localStoragePolicyConfigOpt.get().getBasePath()
				+ localStoragePolicyConfigOpt.get().getCoverImagePath());
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		Path filePath = uploadPath.resolve(storageName + "." + extension);
		file.transferTo(filePath.toFile());

		// 保存附件信息
		attachmentRepository.save(attachmentEntity);
		return attachmentEntity;
	}

	@Override
	public void download(String fullStorageName, HttpServletResponse httpServletResponse) {
		String storageName = FileUtil.getFileNameWithoutExtension(fullStorageName);
		Optional<AttachmentEntity> attachmentOpt = attachmentRepository.findByStorageName(storageName);
		if (attachmentOpt.isEmpty()) {
			throw new DataNotFoundException("附件不存在或已被删除");
		}
		AttachmentEntity attachmentEntity = attachmentOpt.get();

		// 构建文件路径
		File file = new File(attachmentEntity.getPath());

		// 检查文件是否存在
		if (!file.exists()) {
			throw new DataNotFoundException("附件不存在或已被删除");
		}

		// 设置响应头，指明文件类型和下载时的文件名
		httpServletResponse.setContentType("application/octet-stream");
		httpServletResponse.setHeader("Content-Disposition",
				"attachment; filename=\"" + attachmentEntity.getFullStorageName() + "\"");
		httpServletResponse.setContentLengthLong(file.length());

		// 将文件内容通过输出流返回给前端
		try (InputStream inputStream = new FileInputStream(file)) {
			byte[] buffer = new byte[1024];
			int bytesRead;

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				httpServletResponse.getOutputStream().write(buffer, 0, bytesRead);
			}

			httpServletResponse.getOutputStream().flush();
		}
		catch (IOException e) {
			log.error("下载文件失败", e);
			throw new BusinessException("下载文件失败");
		}
	}

	@Override
	public String getDownloadUrl(String fullStorageName) {
		// todo:改为博客配置的域名
		return "http://" + IpUtil.getLocalHostAddress() + ":" + env.getProperty("server.port")
				+ "/admin/attachments/download/" + fullStorageName;
	}

}

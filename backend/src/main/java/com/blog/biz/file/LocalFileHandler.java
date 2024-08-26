package com.blog.biz.file;

import com.blog.biz.enums.StoragePolicy;
import com.blog.biz.model.config.LocalStoragePolicyConfig;
import com.blog.biz.model.entity.AttachmentEntity;
import com.blog.biz.service.AttachmentService;
import com.blog.biz.service.ConfigService;
import com.blog.common.exception.BusinessException;
import com.blog.common.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

/**
 * @author Gudao
 * @since 2024/8/16
 */
@RequiredArgsConstructor
@Component
public class LocalFileHandler implements FileHandler {

	private final ConfigService configService;

	private final AttachmentService attachmentService;

	@Override
	public boolean support(StoragePolicy storagePolicy) {
		return StoragePolicy.LOCAL.equals(storagePolicy);
	}

	@SneakyThrows
	@Override
	public AttachmentEntity upload(File file) {
		Assert.notNull(file, "The upload file cannot be null");
		// 文件绝对路径MD5加密作为存储名称
		String storageName = DigestUtils.md5Hex(file.getAbsolutePath());
		// 文件hash值
		String parsedFileHash = DigestUtils.md5Hex(Files.readAllBytes(file.toPath()));

		Optional<AttachmentEntity> attachmentOpt = attachmentService.findByStorageName(storageName);

		// 文件未上传或文件内容发生变更，需要上传
		if (attachmentOpt.isEmpty() || !StringUtils.equals(parsedFileHash, attachmentOpt.get().getFileHash())) {

			Optional<LocalStoragePolicyConfig> localStoragePolicyConfigOpt = configService.loadLocalStoragePolicy();
			if (localStoragePolicyConfigOpt.isEmpty()) {
				throw new BusinessException("未配置本地存储配置信息");
			}

			String extension = FilenameUtils.getExtension(file.getName());
			// 本地上传后的路径
			String path = localStoragePolicyConfigOpt.get().getMarkdownImageFullPath() + File.separator + storageName
					+ "." + extension;
			AttachmentEntity attachmentEntity = attachmentOpt.orElseGet(AttachmentEntity::new);
			attachmentEntity.setOriginalName(file.getName())
				.setStorageName(storageName)
				.setOriginalName(FileUtil.getFileNameWithoutExtension(file.getName()))
				.setExtension(FilenameUtils.getExtension(file.getName()))
				.setPath(path)
				.setSize(FileUtil.getFileKbSize(file))
				.setStoragePolicy(StoragePolicy.LOCAL)
				.setFileHash(DigestUtils.md5Hex(Files.readAllBytes(file.toPath())));
			// 复制文件
			FileUtils.copyFile(file, new File(path));
			// 保存附件信息
			attachmentService.saveOrUpdate(attachmentEntity);
			return attachmentEntity;
		}

		return attachmentOpt.get();
	}

}

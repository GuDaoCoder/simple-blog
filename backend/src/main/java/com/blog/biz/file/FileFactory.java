package com.blog.biz.file;

import com.blog.biz.model.config.StoragePolicyConfig;
import com.blog.biz.service.ConfigService;
import com.blog.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author Gudao
 * @since 2024/8/16
 */
@RequiredArgsConstructor
@Component
public class FileFactory {

	private final List<FileHandler> fileHandlers;

	private final ConfigService configService;

	/**
	 * 根据存储策略选择对应的FileHandler
	 * @return FileHandler
	 **/
	public FileHandler getFileHandler() {
		Optional<StoragePolicyConfig> storagePolicyOpt = configService.loadStoragePolicy();
		if (storagePolicyOpt.isEmpty()) {
			throw new BusinessException("The storage policy cannot be null");
		}
		return fileHandlers.stream()
			.filter(fileHandler -> fileHandler.support(storagePolicyOpt.get().getPolicy()))
			.findFirst()
			.orElseThrow(() -> new BusinessException("The storage policy is not supported"));
	}

}

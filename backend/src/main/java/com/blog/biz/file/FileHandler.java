package com.blog.biz.file;

import com.blog.biz.enums.StoragePolicy;
import com.blog.biz.model.entity.AttachmentEntity;

import java.io.File;

/**
 * @author Gudao
 * @since 2024/8/16
 */
public interface FileHandler {

	/**
	 * 是否支持
	 * @return
	 */
	boolean support(StoragePolicy storagePolicy);

	/**
	 * 上传文件
	 * @param file
	 * @return AttachmentEntity
	 **/
	AttachmentEntity upload(File file);

}

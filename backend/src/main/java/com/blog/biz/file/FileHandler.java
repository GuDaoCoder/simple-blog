package com.blog.biz.file;

import com.blog.biz.enums.AttachmentModule;
import com.blog.biz.enums.StoragePolicy;
import com.blog.biz.model.entity.AttachmentEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

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
	 * @param module
	 * @return AttachmentEntity
	 **/
	AttachmentEntity upload(File file, AttachmentModule module);

	/**
	 * 上传文件
	 * @param file
	 * @param module
	 * @return AttachmentEntity
	 **/
	AttachmentEntity upload(MultipartFile file, AttachmentModule module);

	/**
	 * 下载文件
	 * @param fullStorageName
	 * @param httpServletResponse
	 * @return void
	 **/
	void download(String fullStorageName, HttpServletResponse httpServletResponse);

	/**
	 * 获取下载链接
	 * @param fullStorageName
	 * @return String
	 **/
	String getDownloadUrl(String fullStorageName);

}

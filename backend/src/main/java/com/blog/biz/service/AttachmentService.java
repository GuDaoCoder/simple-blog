package com.blog.biz.service;

import com.blog.biz.model.request.AttachmentQueryRequest;
import com.blog.biz.model.request.AttachmentUploadRequest;
import com.blog.biz.model.response.AttachmentResponse;
import com.blog.biz.model.response.AttachmentUploadResponse;
import com.blog.common.base.PageResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Gudao
 * @since 2024/8/16
 */
public interface AttachmentService {

	/**
	 * 分页查询附件信息
	 * @param request
	 * @return PageResponse<AttachmentResponse>
	 **/
	PageResponse<AttachmentResponse> query(AttachmentQueryRequest request);

	/**
	 * 上传文件
	 * @param file
	 * @param request
	 * @return AttachmentUploadResponse
	 **/
	AttachmentUploadResponse upload(@RequestParam("file") MultipartFile file, AttachmentUploadRequest request);

	/**
	 * 下载文件
	 * @param fullStorageName
	 * @param httpServletResponse
	 * @return void
	 **/
	void download(String fullStorageName, HttpServletResponse httpServletResponse);

}

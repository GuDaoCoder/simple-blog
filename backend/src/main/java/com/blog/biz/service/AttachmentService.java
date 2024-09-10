package com.blog.biz.service;

import com.blog.biz.model.entity.AttachmentEntity;
import com.blog.biz.model.request.AttachmentQueryRequest;
import com.blog.biz.model.response.AttachmentResponse;
import com.blog.common.base.PageResponse;

import java.util.Optional;

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
	 * 根据附件存储名称查询附件信息
	 * @param storageName
	 * @return Optional<AttachmentEntity>
	 **/
	Optional<AttachmentEntity> findByStorageName(String storageName);

	/**
	 * 新增或更新附件信息
	 * @param attachmentEntity
	 * @return void
	 **/
	void saveOrUpdate(AttachmentEntity attachmentEntity);

}

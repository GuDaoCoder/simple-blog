package com.blog.biz.service;

import com.blog.biz.model.entity.AttachmentEntity;

import java.util.Optional;

/**
 * @author Gudao
 * @since 2024/8/16
 */
public interface AttachmentService {

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

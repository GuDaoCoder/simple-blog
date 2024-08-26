package com.blog.biz.repository;

import com.blog.biz.model.entity.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Gudao
 * @since 2024/8/16
 */
@Repository
public interface AttachmentRepository
		extends JpaRepository<AttachmentEntity, Long>, QuerydslPredicateExecutor<AttachmentEntity> {

	AttachmentEntity findByStorageName(String storageName);

}

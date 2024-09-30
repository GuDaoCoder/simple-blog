package com.blog.biz.repository;

import com.blog.biz.model.entity.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Gudao
 * @since 2024/8/16
 */
@Repository
public interface AttachmentRepository
		extends JpaRepository<AttachmentEntity, Long>, JpaSpecificationExecutor<AttachmentEntity> {

	Optional<AttachmentEntity> findByStorageName(String storageName);

}

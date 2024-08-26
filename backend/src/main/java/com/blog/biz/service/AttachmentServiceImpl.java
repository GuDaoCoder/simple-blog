package com.blog.biz.service;

import com.blog.biz.model.entity.AttachmentEntity;
import com.blog.biz.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Gudao
 * @since 2024/8/16
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AttachmentServiceImpl implements AttachmentService {

	private final AttachmentRepository attachmentRepository;

	@Override
	public Optional<AttachmentEntity> findByStorageName(String storageName) {
		return Optional.ofNullable(attachmentRepository.findByStorageName(storageName));
	}

	@Override
	public void saveOrUpdate(AttachmentEntity attachmentEntity) {
		attachmentRepository.save(attachmentEntity);
	}

}

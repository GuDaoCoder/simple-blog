package com.blog.biz.service.impl;

import com.blog.biz.mapper.AttachmentMapper;
import com.blog.biz.model.entity.AttachmentEntity;
import com.blog.biz.model.request.AttachmentQueryRequest;
import com.blog.biz.model.response.AttachmentResponse;
import com.blog.biz.repository.AttachmentRepository;
import com.blog.biz.service.AttachmentService;
import com.blog.common.base.PageResponse;
import com.blog.common.jpa.query.QuerySpecificationBuilder;
import com.blog.common.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
	public PageResponse<AttachmentResponse> query(AttachmentQueryRequest request) {
		Page<AttachmentEntity> page = attachmentRepository.findAll(QuerySpecificationBuilder.build(request),
				request.pageable());
		return PageUtil.toResult(page, AttachmentMapper.INSTANCE::toResponse);
	}

	@Override
	public Optional<AttachmentEntity> findByStorageName(String storageName) {
		return Optional.ofNullable(attachmentRepository.findByStorageName(storageName));
	}

	@Override
	public void saveOrUpdate(AttachmentEntity attachmentEntity) {
		attachmentRepository.save(attachmentEntity);
	}

}

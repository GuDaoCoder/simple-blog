package com.blog.biz.service.impl;

import com.blog.biz.file.FileFactory;
import com.blog.biz.mapper.AttachmentMapper;
import com.blog.biz.model.entity.AttachmentEntity;
import com.blog.biz.model.request.AttachmentQueryRequest;
import com.blog.biz.model.request.AttachmentUploadRequest;
import com.blog.biz.model.response.AttachmentResponse;
import com.blog.biz.model.response.AttachmentUploadResponse;
import com.blog.biz.repository.AttachmentRepository;
import com.blog.biz.service.AttachmentService;
import com.blog.common.base.PageResponse;
import com.blog.common.exception.BusinessException;
import com.blog.common.jpa.query.QuerySpecificationBuilder;
import com.blog.common.util.PageUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

	private final FileFactory fileFactory;

	@Override
	public PageResponse<AttachmentResponse> query(AttachmentQueryRequest request) {
		Page<AttachmentEntity> page = attachmentRepository.findAll(QuerySpecificationBuilder.build(request),
				request.pageable());
		return PageUtil.toResult(page, AttachmentMapper.INSTANCE::toResponse);
	}

	@Override
	public AttachmentUploadResponse upload(MultipartFile file, AttachmentUploadRequest request) {
		if (file == null || file.isEmpty()) {
			throw new BusinessException("上传文件不能为空");
		}
		AttachmentEntity attachmentEntity = fileFactory.getFileHandler().upload(file, request.getModule());
		AttachmentUploadResponse response = AttachmentMapper.INSTANCE.toUploadResponse(attachmentEntity);
		response.setUrl(fileFactory.getFileHandler().getDownloadUrl(attachmentEntity.getFullStorageName()));
		return response;
	}

	@Override
	public void download(String fullStorageName, HttpServletResponse httpServletResponse) {
		fileFactory.getFileHandler().download(fullStorageName, httpServletResponse);
	}

}

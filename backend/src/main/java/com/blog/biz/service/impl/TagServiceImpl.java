package com.blog.biz.service.impl;

import com.blog.biz.mapper.TagMapper;
import com.blog.biz.model.entity.TagEntity;
import com.blog.biz.model.request.TagQueryRequest;
import com.blog.biz.model.response.TagResponse;
import com.blog.biz.repository.TagRepository;
import com.blog.biz.service.TagService;
import com.blog.common.base.PageResponse;
import com.blog.common.jpa.query.QuerySpecificationBuilder;
import com.blog.common.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Gudao
 * @since 2024/8/5
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

	private final TagRepository tagRepository;

	@Override
	public PageResponse<TagResponse> query(TagQueryRequest request) {
		Page<TagEntity> page = tagRepository.findAll(QuerySpecificationBuilder.build(request), request.pageable());
		return PageUtil.toResult(page, TagMapper.INSTANCE::toResponse);
	}

	@Override
	public List<TagResponse> queryAll() {
		return tagRepository.findAll().stream().map(TagMapper.INSTANCE::toResponse).toList();
	}

}

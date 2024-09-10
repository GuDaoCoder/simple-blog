package com.blog.biz.service.impl;

import com.blog.biz.mapper.ArticleMapper;
import com.blog.biz.model.entity.ArticleContentEntity;
import com.blog.biz.model.entity.ArticleEntity;
import com.blog.biz.model.request.ArticleQueryRequest;
import com.blog.biz.model.response.ArticleResponse;
import com.blog.biz.repository.ArticleContentRepository;
import com.blog.biz.repository.ArticleRepository;
import com.blog.biz.service.ArticleService;
import com.blog.common.base.PageResponse;
import com.blog.common.jpa.query.QuerySpecificationBuilder;
import com.blog.common.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepository;

	private final ArticleContentRepository articleContentRepository;

	@Override
	public PageResponse<ArticleResponse> query(ArticleQueryRequest request) {
		Page<ArticleEntity> page = articleRepository.findAll(QuerySpecificationBuilder.build(request),
				request.pageable());
		return PageUtil.toResult(page, ArticleMapper.INSTANCE::toResponse);
	}

	@Override
	public String getContent(Long articleId) {
		return Optional.ofNullable(articleContentRepository.findByArticleId(articleId))
			.map(ArticleContentEntity::getContent)
			.orElse(null);
	}

}

package com.blog.biz.service.impl;

import com.blog.biz.enums.ArticleStatus;
import com.blog.biz.mapper.ArticleMapper;
import com.blog.biz.mapper.CategoryMapper;
import com.blog.biz.mapper.TagMapper;
import com.blog.biz.model.entity.*;
import com.blog.biz.model.request.ArticleCoverImageUrlRequest;
import com.blog.biz.model.request.ArticlePortalQueryRequest;
import com.blog.biz.model.request.ArticleQueryRequest;
import com.blog.biz.model.response.ArticleDetailResponse;
import com.blog.biz.repository.*;
import com.blog.biz.service.ArticleService;
import com.blog.common.base.PageResponse;
import com.blog.common.exception.BusinessException;
import com.blog.common.exception.DataNotFoundException;
import com.blog.common.jpa.query.QuerySpecificationBuilder;
import com.blog.common.util.DateTimeUtil;
import com.blog.common.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepository;

	private final ArticleContentRepository articleContentRepository;

	private final ArticleTagRepository articleTagRepository;

	private final TagRepository tagRepository;

	private final CategoryRepository categoryRepository;

	@Override
	public PageResponse<ArticleDetailResponse> query(ArticleQueryRequest request) {
		Page<ArticleEntity> page = articleRepository.findAll(QuerySpecificationBuilder.build(request),
				request.pageable());
		return PageUtil.toCustomResult(page, this::entityToDetailResponse);
	}

	/**
	 * 数据库实体对象转换为详情对象
	 * @param articleEntities
	 * @return List<ArticleDetailResponse>
	 **/
	private List<ArticleDetailResponse> entityToDetailResponse(List<ArticleEntity> articleEntities) {
		if (CollectionUtils.isEmpty(articleEntities)) {
			return List.of();
		}
		Map<Long, List<TagEntity>> articleTagRelation = geArticleTagRelations(
				articleEntities.stream().map(ArticleEntity::getArticleId).collect(Collectors.toSet()));

		Map<Long, CategoryEntity> categoryRelation = getCategoryRelation(articleEntities.stream()
			.map(ArticleEntity::getCategoryId)
			.filter(Objects::nonNull)
			.collect(Collectors.toSet()));

		return articleEntities.stream().map(entity -> {
			ArticleDetailResponse detailResponse = ArticleMapper.INSTANCE.toDetailResponse(entity);
			detailResponse.setCategory(CategoryMapper.INSTANCE.toResponse(categoryRelation.get(entity.getCategoryId())))
				.setTags(articleTagRelation.getOrDefault(entity.getArticleId(), List.of())
					.stream()
					.map(TagMapper.INSTANCE::toResponse)
					.toList());
			return detailResponse;
		}).toList();
	}

	/**
	 * 查询文章标签关系
	 * @param articleIds
	 * @return Map<Long,List<TagEntity>>
	 **/
	private Map<Long, List<TagEntity>> geArticleTagRelations(Set<Long> articleIds) {
		if (CollectionUtils.isEmpty(articleIds)) {
			return Map.of();
		}
		List<ArticleTagEntity> articleTagEntities = articleTagRepository.findAllByArticleIdIn(articleIds);
		if (CollectionUtils.isEmpty(articleTagEntities)) {
			return Map.of();
		}
		// 所有的标签Id集合
		Set<Long> tagIds = articleTagEntities.stream().map(ArticleTagEntity::getTagId).collect(Collectors.toSet());
		List<TagEntity> tagEntities = tagRepository.findAllById(tagIds);

		// 文章Id和标签Id的关系
		Map<Long, List<Long>> articleTagIdMap = articleTagEntities.stream()
			.collect(Collectors.groupingBy(ArticleTagEntity::getArticleId,
					Collectors.mapping(ArticleTagEntity::getTagId, Collectors.toList())));

		return articleTagIdMap.entrySet()
			.stream()
			.collect(Collectors.toMap(Map.Entry::getKey,
					entry -> tagEntities.stream()
						.filter(tagEntity -> entry.getValue().contains(tagEntity.getTagId()))
						.toList()));
	}

	private Map<Long, CategoryEntity> getCategoryRelation(Set<Long> categoryIds) {
		if (CollectionUtils.isEmpty(categoryIds)) {
			return Map.of();
		}
		return categoryRepository.findAllById(categoryIds)
			.stream()
			.collect(Collectors.toMap(CategoryEntity::getCategoryId, Function.identity()));
	}

	@Override
	public String getContent(Long articleId) {
		return Optional.ofNullable(articleContentRepository.findByArticleId(articleId))
			.map(ArticleContentEntity::getContent)
			.orElse(null);
	}

	@Override
	public void updateCoverImage(Long articleId, ArticleCoverImageUrlRequest request) {
		ArticleEntity articleEntity = articleRepository.findById(articleId)
			.orElseThrow(() -> new DataNotFoundException("文章不存在或已被删除"));
		articleEntity.setCoverImageUrl(request.getCoverImageUrl());
		articleRepository.save(articleEntity);
	}

	@Override
	public void publish(Long articleId) {
		ArticleEntity articleEntity = articleRepository.findById(articleId)
			.orElseThrow(() -> new DataNotFoundException("文章不存在或已被删除"));
		if (!ArticleStatus.UNPUBLISHED.equals(articleEntity.getStatus())) {
			throw new BusinessException("只有未发布的文章可以发布");
		}
		articleEntity.setStatus(ArticleStatus.PUBLISHED).setPublishTime(DateTimeUtil.now());
		articleRepository.save(articleEntity);
	}

	@Override
	public void unPublish(Long articleId) {
		ArticleEntity articleEntity = articleRepository.findById(articleId)
			.orElseThrow(() -> new DataNotFoundException("文章不存在或已被删除"));
		if (!ArticleStatus.PUBLISHED.equals(articleEntity.getStatus())) {
			throw new BusinessException("只有已发布的文章可以下架");
		}
		articleEntity.setStatus(ArticleStatus.UNPUBLISHED).setUnPublishTime(DateTimeUtil.now());
		articleRepository.save(articleEntity);
	}

	@Override
	public PageResponse<ArticleDetailResponse> portalQuery(ArticlePortalQueryRequest request) {
		Page<ArticleEntity> page = articleRepository.findAll(QuerySpecificationBuilder.build(request),
				request.pageable());
		return PageUtil.toCustomResult(page, this::entityToDetailResponse);
	}

	@Override
	public ArticleDetailResponse portalGetDetail(Long articleId) {
		ArticleEntity articleEntity = articleRepository.findById(articleId)
			.orElseThrow(() -> new DataNotFoundException("文章不存在或已被删除"));
		return entityToDetailResponse(List.of(articleEntity)).stream().findFirst().get();
	}

}

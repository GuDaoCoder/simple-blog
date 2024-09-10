package com.blog.biz.service.impl;

import com.blog.biz.mapper.ArticleMapper;
import com.blog.biz.mapper.TagMapper;
import com.blog.biz.model.entity.ArticleContentEntity;
import com.blog.biz.model.entity.ArticleEntity;
import com.blog.biz.model.entity.ArticleTagEntity;
import com.blog.biz.model.entity.TagEntity;
import com.blog.biz.model.request.ArticleQueryRequest;
import com.blog.biz.model.response.ArticleDetailResponse;
import com.blog.biz.model.response.ArticleResponse;
import com.blog.biz.repository.ArticleContentRepository;
import com.blog.biz.repository.ArticleRepository;
import com.blog.biz.repository.ArticleTagRepository;
import com.blog.biz.repository.TagRepository;
import com.blog.biz.service.ArticleService;
import com.blog.common.base.PageResponse;
import com.blog.common.jpa.query.QuerySpecificationBuilder;
import com.blog.common.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
		Map<Long, List<TagEntity>> articleTagMap = geArticleTagRelations(articleEntities);

		return articleEntities.stream().map(entity -> {
			ArticleDetailResponse detailResponse = ArticleMapper.INSTANCE.toDetailResponse(entity);
			detailResponse.setTags(articleTagMap.getOrDefault(entity.getArticleId(), List.of())
				.stream()
				.map(TagMapper.INSTANCE::toResponse)
				.toList());
			return detailResponse;
		}).toList();
	}

	/**
	 * 查询文章标签关系
	 * @param articleEntities
	 * @return Map<Long,List<TagEntity>>
	 **/
	private Map<Long, List<TagEntity>> geArticleTagRelations(List<ArticleEntity> articleEntities) {
		if (CollectionUtils.isEmpty(articleEntities)) {
			return Map.of();
		}
		// 所有的文章Id集合
		Set<Long> articleIds = articleEntities.stream().map(ArticleEntity::getArticleId).collect(Collectors.toSet());
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

	@Override
	public String getContent(Long articleId) {
		return Optional.ofNullable(articleContentRepository.findByArticleId(articleId))
			.map(ArticleContentEntity::getContent)
			.orElse(null);
	}

}

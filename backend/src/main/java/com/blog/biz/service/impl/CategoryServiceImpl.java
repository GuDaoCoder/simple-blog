package com.blog.biz.service.impl;

import com.blog.biz.constant.BizConstants;
import com.blog.biz.constant.SymbolConstants;
import com.blog.biz.mapper.CategoryMapper;
import com.blog.biz.model.entity.CategoryEntity;
import com.blog.biz.model.request.CategoryQueryRequest;
import com.blog.biz.model.response.CategoryNodeResponse;
import com.blog.biz.repository.ArticleRepository;
import com.blog.biz.repository.CategoryRepository;
import com.blog.biz.service.CategoryService;
import com.blog.common.jpa.query.QuerySpecificationBuilder;
import com.blog.common.util.TreeUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Gudao
 * @since 2024/8/7
 */
@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	private final ArticleRepository articleRepository;

	@Override
	public List<CategoryNodeResponse> tree(CategoryQueryRequest request) {
		return buildCategoryTree(categoryRepository.findAll(QuerySpecificationBuilder.build(request)));
	}

	@Override
	public List<CategoryNodeResponse> portalTree() {
		return buildCategoryTree(categoryRepository.findAll());
	}

	/**
	 * 构建分类树API返回
	 * @param categoryEntities
	 * @return List<CategoryNodeResponse>
	 **/
	private List<CategoryNodeResponse> buildCategoryTree(List<CategoryEntity> categoryEntities) {
		if (CollectionUtils.isEmpty(categoryEntities)) {
			return new ArrayList<>();
		}
		Set<Long> categoryIds = categoryEntities.stream()
			.flatMap(entity -> Arrays.stream(entity.getFullId().split(SymbolConstants.CENTER_LINE)))
			.map(Long::valueOf)
			.filter(id -> !id.equals(BizConstants.ROOT_ID))
			.collect(Collectors.toSet());

		// 可能根据条件查询出来就是所有的分类，不需要再去查询所有上级
		categoryEntities.stream().map(CategoryEntity::getCategoryId).forEach(categoryIds::remove);

		if (CollectionUtils.isNotEmpty(categoryIds)) {
			categoryEntities.addAll(categoryRepository.findAllById(categoryIds));
		}

		categoryIds = categoryEntities.stream().map(CategoryEntity::getCategoryId).collect(Collectors.toSet());
		Map<String, Integer> articleCountMap = articleRepository.findCountByCategoryId(categoryIds)
			.stream()
			.collect(Collectors.toMap(o -> String.valueOf(o[0]), o -> ((Number) o[1]).intValue()));

		List<CategoryNodeResponse> data = categoryEntities.stream().map(entity -> {
			CategoryNodeResponse nodeResponse = CategoryMapper.INSTANCE.toNodeResponse(entity);
			// 统计每个分类的文章数量
			nodeResponse.setArticleCount(articleCountMap.getOrDefault(String.valueOf(entity.getCategoryId()), 0));
			return nodeResponse;
		}).sorted(Comparator.comparing(CategoryNodeResponse::getOrderNo)).toList();

		return TreeUtil.build(data, BizConstants.ROOT_ID, CategoryNodeResponse::getCategoryId,
				CategoryNodeResponse::getParentCategoryId, CategoryNodeResponse::setChildren);
	}

}

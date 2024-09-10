package com.blog.biz.mapper;

import com.blog.biz.model.entity.ArticleEntity;
import com.blog.biz.model.response.ArticleDetailResponse;
import com.blog.biz.model.response.ArticleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * @author Gudao
 * @since 2024/9/10
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArticleMapper {

	ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

	ArticleResponse toResponse(ArticleEntity entity);

	ArticleDetailResponse toDetailResponse(ArticleEntity entity);

}

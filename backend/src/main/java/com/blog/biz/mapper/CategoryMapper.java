package com.blog.biz.mapper;

import com.blog.biz.model.entity.CategoryEntity;
import com.blog.biz.model.response.CategoryNodeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * @author Gudao
 * @since 2024/8/27
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

	CategoryNodeResponse toNodeResponse(CategoryEntity entity);

}

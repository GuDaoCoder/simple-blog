package com.github.biz.mapper;

import com.github.biz.model.entity.TagEntity;
import com.github.biz.model.response.TagResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * @author Gudao
 * @since 2024/7/27
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TagMapper {

	TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

	TagResponse toResponse(TagEntity tagEntity);

}

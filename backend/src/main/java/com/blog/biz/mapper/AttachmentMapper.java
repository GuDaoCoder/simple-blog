package com.blog.biz.mapper;

import com.blog.biz.model.entity.AttachmentEntity;
import com.blog.biz.model.response.AttachmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * @author Gudao
 * @since 2024/9/10
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttachmentMapper {

	AttachmentMapper INSTANCE = Mappers.getMapper(AttachmentMapper.class);

	AttachmentResponse toResponse(AttachmentEntity entity);

}

package com.blog.biz.mapper;

import com.blog.biz.model.config.GitConfig;
import com.blog.biz.model.request.GitConfigRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * @author Gudao
 * @since 2024/8/15
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ConfigMapper {

	ConfigMapper INSTANCE = Mappers.getMapper(ConfigMapper.class);

	GitConfig toGitConfig(GitConfigRequest request);

}

package com.blog.biz.service;

import com.blog.biz.model.config.GitConfigEntity;

import java.util.Optional;

/**
 * @author Gudao
 * @since 2024/8/13
 */
public interface ConfigService {

	/**
	 * 加载配置
	 * @param clazz
	 * @return Optional<T>
	 **/
	<T> Optional<T> load(Class<T> clazz);

	/**
	 * 加载git配置
	 * @return Optional<GitConfigEntity>
	 **/
	Optional<GitConfigEntity> loadGitConfig();

}

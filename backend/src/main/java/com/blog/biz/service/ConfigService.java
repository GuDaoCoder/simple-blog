package com.blog.biz.service;

import com.blog.biz.model.config.GitConfig;

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
	 * 保存配置
	 * @param data
	 * @return void
	 **/
	<T> void save(T data);

	/**
	 * 加载git配置
	 * @return Optional<GitConfig>
	 **/
	Optional<GitConfig> loadGitConfig();

}

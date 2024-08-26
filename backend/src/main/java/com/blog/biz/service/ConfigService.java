package com.blog.biz.service;

import com.blog.biz.model.config.GitConfig;
import com.blog.biz.model.config.LocalStoragePolicyConfig;
import com.blog.biz.model.config.StoragePolicyConfig;

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

	/**
	 * 
	 * 加载存储策略配置
	 * @return Optional<StoragePolicyConfig>
	 **/
	Optional<StoragePolicyConfig> loadStoragePolicy();

	/**
	 * 加载本地存储策略
	 * @return Optional<LocalStoragePolicyConfig>
	 **/
	Optional<LocalStoragePolicyConfig> loadLocalStoragePolicy();

}

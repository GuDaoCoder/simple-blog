package com.github.biz.service;

/**
 * @author Gudao
 * @since 2024/8/13
 */
public interface ConfigService {

	/**
	 * 加载配置
	 * @param clazz
	 * @return T
	 **/
	<T> T load(Class<T> clazz);

}

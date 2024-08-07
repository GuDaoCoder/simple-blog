package com.github.biz.service;

/**
 * @author Gudao
 * @since 2024/8/5
 */
public interface TagService {

	/**
	 * 新增一个tag，颜色随机生成
	 * @param tagName
	 * @return Long
	 **/
	Long addTag(String tagName);

}

package com.blog.biz.model.config;

import com.blog.biz.annotation.Config;
import com.blog.biz.constant.ConfigPrefixConstants;
import lombok.Data;

/**
 * 本地存储策略配置
 *
 * @author Gudao
 * @since 2024/8/16
 */
@Data
@Config(prefix = ConfigPrefixConstants.LOCAL_STORAGE_POLICY)
public class LocalStoragePolicyConfig {

	/**
	 * 基础路径
	 */
	private String basePath;

	/**
	 * markdown图片存储路径
	 */
	private String markdownImagePath;

	public String getMarkdownImageFullPath() {
		return this.getBasePath() + this.getMarkdownImagePath();
	}

}
package com.blog.biz.model.config;

import com.blog.biz.annotation.Config;
import com.blog.biz.constant.ConfigPrefixConstants;
import com.blog.biz.enums.StoragePolicy;
import lombok.Data;

/**
 * 存储策略配置
 *
 * @author Gudao
 * @since 2024/8/16
 */
@Data
@Config(prefix = ConfigPrefixConstants.STORAGE)
public class StoragePolicyConfig {

	/**
	 * 存储策略
	 */
	private StoragePolicy policy;

}
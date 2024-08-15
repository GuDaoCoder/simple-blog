package com.blog.biz.model.config;

import com.blog.biz.annotation.Config;
import com.blog.biz.annotation.ConfigProperty;
import com.blog.biz.constant.ConfigPrefixConstants;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Git配置信息
 *
 * @author Gudao
 * @since 2024/8/13
 */
@Data
@Config(prefix = ConfigPrefixConstants.GIT)
public class GitConfig implements Serializable {

	@Serial
	private static final long serialVersionUID = 6561073467021405986L;

	/**
	 * git地址
	 */
	private String url;

	/**
	 * 分支
	 */
	private String branch;

	/**
	 * 用户名
	 */
	@ConfigProperty(required = false)
	private String username;

	/**
	 * 密码
	 */
	@ConfigProperty(encrypt = true, required = false)
	private String password;

	/**
	 * 本地路径
	 */
	private String localPath;

}

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
@Config(desc = "Git配置", prefix = ConfigPrefixConstants.GIT)
public class GitConfig implements Serializable {

	@Serial
	private static final long serialVersionUID = 6561073467021405986L;

	@ConfigProperty(desc = "git地址")
	private String url;

	@ConfigProperty(desc = "分支")
	private String branch;

	@ConfigProperty(desc = "用户名", required = false)
	private String username;

	@ConfigProperty(desc = "密码", encrypt = true, required = false)
	private String password;

	@ConfigProperty(desc = "本地路径")
	private String localPath;

}

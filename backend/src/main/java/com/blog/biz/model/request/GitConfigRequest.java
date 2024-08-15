package com.blog.biz.model.request;

import com.blog.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * Git配置信息
 *
 * @author Gudao
 * @since 2024/8/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GitConfigRequest extends BaseRequest {

	@Serial
	private static final long serialVersionUID = 330315479083496344L;

	@Schema(description = "git地址")
	private String url;

	@Schema(description = "分支")
	private String branch;

	@Schema(description = "用户名")
	private String username;

	@Schema(description = "密码")
	private String password;

	@Schema(description = "本地路径")
	private String localPath;

}

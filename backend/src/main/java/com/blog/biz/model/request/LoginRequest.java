package com.blog.biz.model.request;

import com.blog.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author Gudao
 * @since 2024/8/2
 */
@Setter
@Getter
public class LoginRequest extends BaseRequest {

	@Serial
	private static final long serialVersionUID = 3804210864969135409L;

	@Schema(description = "用户名")
	private String username;

	@Schema(description = "密码")
	private String password;

}

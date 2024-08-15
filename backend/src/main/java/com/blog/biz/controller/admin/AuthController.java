package com.blog.biz.controller.admin;

import com.blog.biz.constant.CommonConstants;
import com.blog.biz.model.request.LoginRequest;
import com.blog.biz.model.response.LoginResponse;
import com.blog.biz.service.AdministratorAuthService;
import com.blog.common.base.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gudao
 * @since 2024/8/1
 */
@Tag(name = "认证")
@RestController
@RequestMapping(CommonConstants.REQUEST_PREFIX_ADMIN)
@RequiredArgsConstructor
public class AuthController {

	private final AdministratorAuthService administratorAuthService;

	@Operation(summary = "管理员登陆")
	@PostMapping("/login")
	public R<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		return R.success(administratorAuthService.login(loginRequest));
	}

}

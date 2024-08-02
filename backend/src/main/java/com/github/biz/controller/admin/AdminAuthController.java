package com.github.biz.controller.admin;

import com.github.biz.constant.CommonConstants;
import com.github.biz.model.request.LoginRequest;
import com.github.biz.model.response.LoginResponse;
import com.github.biz.service.AdministratorAuthService;
import com.github.common.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gudao
 * @since 2024/8/1
 */
@RestController
@RequestMapping(CommonConstants.REQUEST_PREFIX_ADMIN)
@RequiredArgsConstructor
public class AdminAuthController {

	private final AdministratorAuthService administratorAuthService;

	/**
	 * 管理员登陆
	 * @param loginRequest
	 * @return R<LoginResponse>
	 **/
	@PostMapping("/login")
	public R<LoginResponse> login(LoginRequest loginRequest) {
		return R.success(administratorAuthService.login(loginRequest));
	}

}

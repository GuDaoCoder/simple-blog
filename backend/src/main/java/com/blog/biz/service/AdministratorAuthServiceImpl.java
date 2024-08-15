package com.blog.biz.service;

import cn.dev33.satoken.stp.StpUtil;
import com.blog.biz.model.request.LoginRequest;
import com.blog.biz.model.response.LoginResponse;
import org.springframework.stereotype.Service;

/**
 * @author Gudao
 * @since 2024/8/1
 */
@Service
public class AdministratorAuthServiceImpl implements AdministratorAuthService {

	@Override
	public LoginResponse login(LoginRequest request) {
		StpUtil.login(1L);
		return new LoginResponse(StpUtil.getTokenValue());
	}

}

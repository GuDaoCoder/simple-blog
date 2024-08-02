package com.github.biz.service;

import com.github.biz.model.request.LoginRequest;
import com.github.biz.model.response.LoginResponse;

/**
 * @author Gudao
 * @since 2024/8/1
 */
public interface AdministratorAuthService {

	/**
	 * 登录
	 * @param request
	 * @return LoginResponse
	 **/
	LoginResponse login(LoginRequest request);

}

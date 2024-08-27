package com.blog.biz.service.impl;

import com.blog.biz.service.AdministratorUserService;
import com.blog.common.authorization.UserDetail;
import com.blog.common.authorization.UserDetailLoadService;
import org.springframework.stereotype.Service;

/**
 * @author Gudao
 * @since 2024/8/1
 */
@Service
public class AdministratorUserServiceImpl implements AdministratorUserService, UserDetailLoadService {

	@Override
	public UserDetail load(Long userId) {
		UserDetail userDetail = new UserDetail();
		userDetail.setUserId(1L);
		userDetail.setUsername("Admin");
		return userDetail;
	}

}

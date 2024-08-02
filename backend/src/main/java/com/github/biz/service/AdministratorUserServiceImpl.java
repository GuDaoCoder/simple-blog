package com.github.biz.service;

import com.github.common.authorization.UserDetail;
import com.github.common.authorization.UserDetailLoadService;
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

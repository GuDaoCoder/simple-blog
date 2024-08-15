package com.blog.common.authorization;

/**
 * 获取用户详情信息
 *
 * @author Gudao
 * @since 2024/7/31
 */
public interface UserDetailLoadService {

	UserDetail load(Long userId);

}

package com.blog.common.authorization;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户详情
 *
 * @author Gudao
 * @since 2024/7/31
 */
@Setter
@Getter
public class UserDetail implements Serializable {

	@Serial
	private static final long serialVersionUID = -248739391985568605L;

	/**
	 * 用户Id
	 */
	private Long userId;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

}

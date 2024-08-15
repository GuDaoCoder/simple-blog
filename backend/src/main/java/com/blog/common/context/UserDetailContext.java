package com.blog.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.blog.common.authorization.UserDetail;

import java.util.Optional;

/**
 * @author Gudao
 * @since 2024/7/31
 */
public class UserDetailContext {

	private static final TransmittableThreadLocal<UserDetail> local = new TransmittableThreadLocal<>();

	public static void set(UserDetail userDetail) {
		local.set(userDetail);
	}

	public static UserDetail get() {
		return local.get();
	}

	public static Long getUserId() {
		return Optional.ofNullable(get()).map(UserDetail::getUserId).orElse(-1L);
	}

	public static void remove() {
		local.remove();
	}

}

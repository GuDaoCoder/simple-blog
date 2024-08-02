package com.github.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.github.common.authorization.UserDetail;

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

	public static void remove() {
		local.remove();
	}

}

package com.blog.common.util;

import java.util.UUID;

/**
 * @author Gudao
 * @since 2024/9/30
 */
public class UIdUtil {

	/**
	 * uuid
	 * @param
	 * @return String
	 **/
	public static String uid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}

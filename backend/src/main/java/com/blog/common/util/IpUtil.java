package com.blog.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Gudao
 * @since 2024/8/15
 */
public class IpUtil {

	private static String localHostAddress;

	/**
	 * 获取当前IP地址
	 * @param
	 * @return String
	 **/
	public static String getLocalHostAddress() {
		if (localHostAddress == null) {
			try {
				localHostAddress = InetAddress.getLocalHost().getHostAddress();
			}
			catch (UnknownHostException e) {
				localHostAddress = "unknown";
			}
		}
		return localHostAddress;
	}

}

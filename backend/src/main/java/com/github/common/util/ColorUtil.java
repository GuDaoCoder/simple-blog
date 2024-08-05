package com.github.common.util;

import java.security.SecureRandom;

/**
 * 颜色相关工具类
 *
 * @author Gudao
 * @since 2024/8/5
 */
public class ColorUtil {

	/**
	 * 生成随机颜色
	 * @return String
	 **/
	public static String generateHexColor() {
		SecureRandom random = new SecureRandom();
		int rgb = random.nextInt(0xFFFFFF + 1);
		return String.format("#%06x", rgb);
	}

}

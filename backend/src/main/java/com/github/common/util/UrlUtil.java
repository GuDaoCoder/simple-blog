package com.github.common.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Url工具类
 *
 * @author Gudao
 * @since 2024/8/2
 */
public class UrlUtil {

	/**
	 * 判断url是否有效
	 * @param url
	 * @return boolean
	 **/
	public static boolean isValidUrl(String url) {
		try {
			new URL(url).toURI();
			return true;
		}
		catch (MalformedURLException | URISyntaxException e) {
			return false;
		}
	}

}

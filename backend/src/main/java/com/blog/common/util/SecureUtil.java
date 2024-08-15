package com.blog.common.util;

import cn.dev33.satoken.secure.SaSecureUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Gudao
 * @since 2024/8/14
 */
@Component
public class SecureUtil {

	private static String privateKey;

	private static String publicKey;

	public SecureUtil(@Value("${security.rsa.private-key}") String privateKey,
			@Value("${security.rsa.public-key}") String publicKey) {
		SecureUtil.privateKey = privateKey;
		SecureUtil.publicKey = publicKey;
	}

	/**
	 * 加密
	 * @param text
	 * @return String
	 **/
	public static String encrypt(String text) {
		return SaSecureUtil.rsaEncryptByPublic(publicKey, text);
	}

	/**
	 * 解密
	 * @param text
	 * @return String
	 **/
	public static String decrypt(String text) {
		return SaSecureUtil.rsaDecryptByPrivate(privateKey, text);
	}

	public static void main(String[] args) {
		System.out.println(SaSecureUtil.rsaEncryptByPublic(
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/w2Y49BBaaCTSHnTxIdI0MGrhwTxFb2uNiHDjHw/w4bhXEy1X8WcPoqqNwqlHWZvrImvSprh3OtUS4GcWT/6UKQD/VA1iHhZ3m1xQ/CERpivZhOXRznWKGdpufCoYdjFZO7bqm4tGzyPkNGjblIQTRmWrG6lEwmE74EGVl/9q+QIDAQAB",
				"zhangsan"));
	}

}

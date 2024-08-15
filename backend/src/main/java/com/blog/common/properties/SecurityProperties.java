package com.blog.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * @author Gudao
 * @since 2024/7/31
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "security", ignoreInvalidFields = true)
public class SecurityProperties {

	/**
	 * rsa配置信息
	 */
	private Rsa rsa;

	/**
	 * 白名单
	 */
	private Set<String> whiteUrls;

	@Setter
	@Getter
	public static class Rsa {

		/**
		 * 私钥
		 */
		private String privateKey;

		/**
		 * 公钥
		 */
		private String publicKey;

	}

}

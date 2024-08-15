package com.blog.common.exception;

import org.slf4j.helpers.MessageFormatter;

import java.io.Serial;

/**
 * 认证信息无效异常
 *
 * @author Gudao
 * @since 2024/8/2
 */
public class InvalidCredentialsException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -3516586981306583668L;

	public InvalidCredentialsException(String errorMsg, Object... arguments) {
		super(MessageFormatter.arrayFormat(errorMsg, arguments).getMessage());
	}

}

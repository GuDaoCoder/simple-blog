package com.blog.common.exception;

import org.slf4j.helpers.MessageFormatter;

import java.io.Serial;

/**
 * 业务异常
 *
 * @author Gudao
 * @since 2024/7/28
 */
public class BusinessException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 3591569001846853288L;

	public BusinessException(String errorMsg, Object... arguments) {
		super(MessageFormatter.arrayFormat(errorMsg, arguments).getMessage());
	}

	public BusinessException(Throwable ex, String errorMsg, Object... arguments) {
		super(MessageFormatter.arrayFormat(errorMsg, arguments).getMessage(), ex);
	}

}
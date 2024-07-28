package com.github.common.exception;

import org.slf4j.helpers.MessageFormatter;

/**
 * 业务异常
 *
 * @author Gudao
 * @since 2024/7/28
 */
public class BusinessException extends RuntimeException {

	public BusinessException(String errorMsg, Object... arguments) {
		super(MessageFormatter.arrayFormat(errorMsg, arguments).getMessage());
	}

}
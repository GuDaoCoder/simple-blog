package com.blog.common.exception;

import org.slf4j.helpers.MessageFormatter;

import java.io.Serial;

/**
 * jpa query构建异常
 *
 * @author Gudao
 * @since 2024/8/27
 */
public class QueryBuilderException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 8587256070924316189L;

	public QueryBuilderException(String errorMsg) {
		super(errorMsg);
	}

	public QueryBuilderException(String errorMsg, Object... arguments) {
		super(MessageFormatter.arrayFormat(errorMsg, arguments).getMessage());
	}

}

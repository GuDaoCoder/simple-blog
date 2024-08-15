package com.blog.common.exception;

import org.slf4j.helpers.MessageFormatter;

/**
 * 数据未找到异常
 *
 * @author Gudao
 * @since 2024/7/28
 */
public class DataNotFoundException extends RuntimeException {

	public DataNotFoundException(String errorMsg, Object... args) {
		super(MessageFormatter.arrayFormat(errorMsg, args).getMessage());
	}

}

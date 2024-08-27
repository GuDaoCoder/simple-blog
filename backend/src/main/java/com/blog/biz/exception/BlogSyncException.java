package com.blog.biz.exception;

import com.blog.common.exception.BusinessException;

import java.io.Serial;

/**
 * 博客同步异常
 *
 * @author Gudao
 * @since 2024/8/15
 */
public class BlogSyncException extends BusinessException {

	@Serial
	private static final long serialVersionUID = -3587900564437608437L;

	public BlogSyncException(String errorMsg, Object... arguments) {
		super(errorMsg, arguments);
	}

	public BlogSyncException(Throwable ex, String errorMsg, Object... arguments) {
		super(ex, errorMsg, arguments);
	}

}

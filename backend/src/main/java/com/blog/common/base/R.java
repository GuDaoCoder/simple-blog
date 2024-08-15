package com.blog.common.base;

import com.blog.common.util.DateTimeUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 请求返回统一包装类
 *
 * @author Gudao
 * @since 2024/8/1
 */
@Setter
@Getter
public class R<T> implements Serializable {

	/**
	 * 请求时间戳
	 */
	private final Long timestamp;

	/**
	 * 错误信息
	 */
	private final String errorMsg;

	/**
	 * 返回数据
	 */
	private final T data;

	public R(T data, String errorMsg) {
		this.timestamp = DateTimeUtil.nowTimestamp();
		this.data = data;
		this.errorMsg = errorMsg;
	}

	public static <T> R<T> success(T data) {
		return new R<>(data, null);
	}

	public static <T> R<T> success() {
		return success(null);
	}

	public static <T> R<T> fail(String errorMsg) {
		return new R<>(null, errorMsg);
	}

}

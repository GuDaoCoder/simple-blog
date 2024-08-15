package com.blog.common.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 请求入参实体抽象基类
 *
 * @author Gudao
 * @since 2024/8/1
 */
@Setter
@Getter
public abstract class BaseRequest implements Serializable {

	/**
	 * 请求Id
	 */
	private String requestId;

}

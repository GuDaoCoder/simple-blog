package com.blog.biz.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Gudao
 * @since 2024/8/2
 */
@Setter
@Getter
public class TagRequest {

	/**
	 * 标签Id
	 */
	private Long tagId;

	/**
	 * 标签名称
	 */
	private String tagName;

	/**
	 * 颜色
	 */
	private String color;

}

package com.blog.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@AllArgsConstructor
@Getter
public enum ArticleStatus {
	
	PUBLISHED("已发布"),

	UNPUBLISHED("未发布"),;

	private final String label;

}

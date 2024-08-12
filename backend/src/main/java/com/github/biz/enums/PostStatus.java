package com.github.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@AllArgsConstructor
@Getter
public enum PostStatus {

	DRAFT("草稿"),

	PUBLISHED("已发布"),

	REMOVED("已下架"),;

	private final String label;

}

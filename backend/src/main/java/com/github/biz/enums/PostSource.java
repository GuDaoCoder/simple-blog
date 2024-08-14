package com.github.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@Getter
@AllArgsConstructor
public enum PostSource {

	GIT_SYNC("Git同步"),

	;

	private final String label;

}
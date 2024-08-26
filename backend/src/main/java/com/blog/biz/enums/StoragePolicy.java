package com.blog.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 存储策略
 *
 * @author Gudao
 * @since 2024/8/16
 */
@AllArgsConstructor
@Getter
public enum StoragePolicy {

	LOCAL("本地"),;

	private final String label;

}

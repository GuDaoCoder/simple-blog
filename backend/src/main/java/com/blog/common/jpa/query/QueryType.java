package com.blog.common.jpa.query;

/**
 * @author Gudao
 * @since 2024/8/27
 */
public enum QueryType {

	/**
	 * 等于
	 */
	EQUALS,

	/**
	 * 不等于
	 */
	NOT_EQUALS,

	/**
	 * in
	 */
	IN,

	/**
	 * not in
	 */
	NOT_IN,

	/**
	 * 模糊查询
	 */
	LIKE,

	/**
	 * 左模糊查询
	 */
	LEFT_LIKE,

	/**
	 * 右模糊查询
	 */
	RIGHT_LIKE,

	/**
	 * 大于
	 */
	GREATER_THAN,

	GREATER_THAN_EQUAL,

	/**
	 * 小于
	 */
	LESS_THAN,

	LESS_THAN_EQUAL;

}

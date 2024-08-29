package com.blog.common.jpa.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Gudao
 * @since 2024/8/27
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

	/**
	 * 查询的属性名称，默认字段名称
	 * @return String
	 **/
	String propName() default "";

	/**
	 * 查询类型
	 * @return QueryType
	 **/
	QueryType type() default QueryType.EQUALS;

}

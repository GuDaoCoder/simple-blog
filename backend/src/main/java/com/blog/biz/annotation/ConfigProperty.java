package com.blog.biz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Gudao
 * @since 2024/8/13
 */
@Target(value = { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigProperty {

	String name() default "";

	boolean encrypt() default false;

	boolean required() default true;

}

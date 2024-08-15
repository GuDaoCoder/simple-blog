package com.blog.common.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类
 *
 * @author Gudao
 * @since 2024/7/28
 */
public final class DateTimeUtil {

	/**
	 * 标准日期时间格式
	 */
	public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 当前时间
	 * @return LocalDateTime
	 **/
	public static LocalDateTime now() {
		return LocalDateTime.now();
	}

	/**
	 * 当前时间格式化
	 * @return String
	 **/
	public static String nowFormat() {
		return nowFormat(STANDARD_FORMAT);
	}

	/**
	 * 当前时间格式化
	 * @param formatter
	 * @return String
	 **/
	public static String nowFormat(String formatter) {
		return now().format(DateTimeFormatter.ofPattern(formatter));
	}

	/**
	 * 获取当前时间戳
	 * @return Long
	 **/
	public static Long nowTimestamp() {
		return timestamp(now());
	}

	/**
	 * LocalDateTime转换为时间戳
	 * @param localDateTime
	 * @return Long
	 **/
	public static Long timestamp(LocalDateTime localDateTime) {
		return localDateTime.atOffset(ZoneOffset.UTC).toEpochSecond();
	}

	/**
	 * 格式化时间
	 * @param localDateTime
	 * @param formatter
	 * @return String
	 **/
	public static String format(LocalDateTime localDateTime, String formatter) {
		return localDateTime.format(DateTimeFormatter.ofPattern(formatter));
	}

	/**
	 * 字符串转换成LocalDateTime格式
	 * @param dateTime
	 * @param formatter
	 * @return LocalDateTime
	 **/
	public static LocalDateTime parse(String dateTime, String formatter) {
		return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(formatter));
	}

	/**
	 * 字符串转换成LocalDateTime格式
	 * @param dateTime
	 * @return LocalDateTime
	 **/
	public static LocalDateTime parse(String dateTime) {
		return parse(dateTime, STANDARD_FORMAT);
	}

}

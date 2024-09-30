package com.blog.common.util;

import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author Gudao
 * @since 2024/8/17
 */
public class FileUtil {

	/**
	 * 获取文件大小，单位KB
	 * @param file
	 * @return long
	 **/
	public static long getFileKbSize(File file) {
		Assert.notNull(file, "The file cannot be null");
		return (file.length() + 1023) / 1024;
	}

	/**
	 * 获取文件大小，单位KB
	 * @param file
	 * @return long
	 **/
	public static long getFileKbSize(MultipartFile file) {
		Assert.notNull(file, "The file cannot be null");
		return file.getSize() / 1024;
	}

	/**
	 * 获取不带后缀的文件名
	 * @param fileName
	 * @return String
	 **/
	public static String getFileNameWithoutExtension(String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			return fileName;
		}
		int dotIndex = fileName.lastIndexOf('.');

		if (dotIndex == -1 || dotIndex == 0) {
			return fileName;
		}
		return fileName.substring(0, dotIndex);
	}

}

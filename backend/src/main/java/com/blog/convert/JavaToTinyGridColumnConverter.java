package com.blog.convert;

import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Gudao
 * @since 2024/9/21
 */
public class JavaToTinyGridColumnConverter {

	public static void main(String[] args) throws ClassNotFoundException {
		if (args.length == 0) {
			System.out.println("请提供Java类的全限定名称作为参数。");
			return;
		}
		// 从args获取类名
		String className = args[0];
		Class<?> clazz = Class.forName(className);

		// 生成tiny-grid-column组件字符串
		String tinyGridColumns = generateTinyGridColumns(clazz);
		System.out.println(tinyGridColumns);
	}

	// 生成tiny-grid-column组件字符串
	public static String generateTinyGridColumns(Class<?> clazz) {
		StringBuilder sb = new StringBuilder();

		// 遍历类及其父类的所有字段
		processFields(clazz, sb);

		return sb.toString();
	}

	// 递归处理类和父类的字段
	private static void processFields(Class<?> clazz, StringBuilder sb) {
		if (clazz == null || clazz == Object.class) {
			return;
		}

		// 递归处理父类的字段
		processFields(clazz.getSuperclass(), sb);

		// 处理当前类的字段
		for (Field field : clazz.getDeclaredFields()) {
			// 跳过静态字段
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}

			// 获取@Schema注解
			Schema schema = field.getAnnotation(Schema.class);

			// 生成tiny-grid-column组件
			if (schema != null) {
				String fieldName = field.getName();
				String title = schema.description();

				// 生成组件字符串并追加到StringBuilder中
				sb.append("<tiny-grid-column field=\"")
					.append(fieldName)
					.append("\" title=\"")
					.append(title)
					.append("\" show-overflow show-header-tip/>\n");
			}
		}
	}

}

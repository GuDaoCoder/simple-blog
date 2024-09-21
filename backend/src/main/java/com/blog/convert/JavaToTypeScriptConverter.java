package com.blog.convert;

import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gudao
 * @since 2024/9/21
 */
public class JavaToTypeScriptConverter {

	public static void main(String[] args) throws ClassNotFoundException {
		if (args.length == 0) {
			System.out.println("请提供Java类的全限定名称作为参数。");
			return;
		}
		// 从args获取类名
		String className = args[0];
		Class<?> clazz = Class.forName(className);

		// 转换为TypeScript interface
		String tsInterface = convertToTypeScript(clazz);
		System.out.println(tsInterface);
	}

	public static String convertToTypeScript(Class<?> clazz) {
		StringBuilder sb = new StringBuilder();

		// 生成TypeScript接口名
		sb.append("interface ").append(clazz.getSimpleName()).append(" {\n");

		// 遍历类的所有字段，包括父类的字段
		processFields(clazz, sb);

		sb.append("}\n");
		return sb.toString();
	}

	// 处理类及其父类的字段
	private static void processFields(Class<?> clazz, StringBuilder sb) {
		if (clazz == null || clazz == Object.class) {
			return;
		}

		// 处理父类字段
		processFields(clazz.getSuperclass(), sb);

		// 处理当前类的字段
		for (Field field : clazz.getDeclaredFields()) {
			// 跳过静态字段
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}

			// 获取@Schema注解
			Schema schema = field.getAnnotation(Schema.class);

			// 添加字段注释
			if (schema != null) {
				sb.append("  /**\n");
				sb.append("   * ").append(schema.description()).append("\n");
				sb.append("   */\n");
			}

			// 转换Java类型为TypeScript类型
			String tsType = mapJavaTypeToTSType(field.getType());

			// 添加字段及其类型
			sb.append("  ").append(field.getName()).append(": ").append(tsType).append(";\n");
		}
	}

	// 将Java类型映射为TypeScript类型
	public static String mapJavaTypeToTSType(Class<?> javaType) {
		Map<Class<?>, String> typeMapping = new HashMap<>();
		typeMapping.put(String.class, "string");
		typeMapping.put(int.class, "number");
		typeMapping.put(Integer.class, "number");
		typeMapping.put(long.class, "number");
		typeMapping.put(Long.class, "number");
		typeMapping.put(float.class, "number");
		typeMapping.put(Float.class, "number");
		typeMapping.put(double.class, "number");
		typeMapping.put(Double.class, "number");
		typeMapping.put(boolean.class, "boolean");
		typeMapping.put(Boolean.class, "boolean");
		typeMapping.put(LocalDateTime.class, "string");

		// 检查是否为枚举类型
		if (javaType.isEnum()) {
			return convertEnumToTSType(javaType);
		}

		return typeMapping.getOrDefault(javaType, "any");
	}

	// 将枚举类型转换为TypeScript的联合类型
	public static String convertEnumToTSType(Class<?> enumType) {
		Object[] enumConstants = enumType.getEnumConstants();
		StringBuilder enumTsType = new StringBuilder();

		for (int i = 0; i < enumConstants.length; i++) {
			// 保留枚举常量的原始大写形式
			enumTsType.append("'").append(enumConstants[i].toString()).append("'");
			if (i < enumConstants.length - 1) {
				enumTsType.append(" | ");
			}
		}
		return enumTsType.toString();
	}

}
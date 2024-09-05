package com.blog.common.util;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gudao
 * @since 2024/9/5
 */
public class ReflectUtil {

	/**
	 * 扫码包下指定注解的类
	 * @param packageName
	 * @param annotationClass
	 * @return Set<Class<?>>
	 **/
	public static Set<Class<?>> scanClassesWithAnnotation(String packageName,
			Class<? extends Annotation> annotationClass) throws ClassNotFoundException {
		// 设置不使用默认的过滤器（默认只扫描带有 @Component 的类）
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

		// 添加注解过滤器
		TypeFilter annotationFilter = new AnnotationTypeFilter(annotationClass);
		scanner.addIncludeFilter(annotationFilter);

		// 扫描指定包，找到所有匹配的 BeanDefinition
		Set<Class<?>> annotatedClasses = new HashSet<>();
		Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(packageName);
		for (BeanDefinition beanDefinition : beanDefinitions) {
			annotatedClasses.add(Class.forName(beanDefinition.getBeanClassName()));
		}

		return annotatedClasses;
	}

}

package com.blog.common.jpa.query;

import com.blog.common.exception.QueryBuilderException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gudao
 * @since 2024/8/27
 */
public class QuerySpecificationBuilder {

	/**
	 * 缓存实体类的字段和注解信息
	 */
	private static final Map<Class<?>, List<AnnotatedField>> cachedAnnotations = new ConcurrentHashMap<>();

	public static <T, S> Specification<T> build(S model) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			List<AnnotatedField> annotatedFields = getAnnotatedFields(model.getClass());
			for (AnnotatedField annotatedField : annotatedFields) {
				Field field = annotatedField.field();
				field.setAccessible(true);
				Object value;
				try {
					value = field.get(model);
				}
				catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}

				if (value != null) {
					Predicate predicate = createPredicate(annotatedField, value, root, criteriaBuilder);
					if (predicate != null) {
						predicates.add(predicate);
					}
				}
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

	/**
	 * 创建Predicate对象
	 * @param annotatedField
	 * @param value
	 * @param root
	 * @param criteriaBuilder
	 * @return Predicate
	 **/
	private static <T> Predicate createPredicate(AnnotatedField annotatedField, Object value, Root<T> root,
			CriteriaBuilder criteriaBuilder) {
		Field field = annotatedField.field();
		Query query = annotatedField.query();
		Class<?> fieldType = field.getType();

		Path<Object> path = root.get(StringUtils.isNotBlank(query.propName()) ? query.propName() : field.getName());
		QueryType type = query.type();

		switch (type) {
			case EQUALS:
				return criteriaBuilder.equal(path, value);
			case NOT_EQUALS:
				return criteriaBuilder.notEqual(path, value);
			case IN:
				if (value instanceof Collection<?> collection) {
					if (collection.isEmpty()) {
						return null;
					}
					return path.in(collection);
				}
				else {
					throw new QueryBuilderException("IN type query requires a Collection type value.");
				}
			case NOT_IN:
				if (value instanceof Collection<?> collection) {
					if (collection.isEmpty()) {
						return null;
					}
					return criteriaBuilder.not(path.in(collection));
				}
				else {
					throw new QueryBuilderException("NOT_IN type query requires a Collection type value.");
				}
			case LIKE:
				return criteriaBuilder.like(path.as(String.class), "%" + value + "%");
			case LEFT_LIKE:
				return criteriaBuilder.like(path.as(String.class), "%" + value);
			case RIGHT_LIKE:
				return criteriaBuilder.like(path.as(String.class), value + "%");
			case GREATER_THAN:
				return criteriaBuilder.greaterThan(path.as((Class<? extends Comparable>) fieldType),
						(Comparable) value);
			case GREATER_THAN_EQUAL:
				return criteriaBuilder.greaterThanOrEqualTo(path.as((Class<? extends Comparable>) fieldType),
						(Comparable) value);
			case LESS_THAN:
				return criteriaBuilder.lessThan(path.as((Class<? extends Comparable>) fieldType), (Comparable) value);
			case LESS_THAN_EQUAL:
				return criteriaBuilder.lessThanOrEqualTo(path.as((Class<? extends Comparable>) fieldType),
						(Comparable) value);
			default:
				return null;
		}
	}

	/**
	 * 获取类中字段属性和对应的Query注解
	 * @param clazz
	 * @return List<AnnotatedField>
	 **/
	private static List<AnnotatedField> getAnnotatedFields(Class<?> clazz) {
		// 从缓存中获取注解信息
		return cachedAnnotations.computeIfAbsent(clazz, key -> {
			List<AnnotatedField> annotatedFields = new ArrayList<>();
			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(Query.class)) {
					Query queryAnnotation = field.getAnnotation(Query.class);
					annotatedFields.add(new AnnotatedField(field, queryAnnotation));
				}
			}
			return annotatedFields;
		});
	}

	private record AnnotatedField(Field field, Query query) {

	}

}

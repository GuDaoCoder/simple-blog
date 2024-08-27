package com.blog.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 树工具类
 *
 * @author Gudao
 * @since 2024/8/27
 */
public class TreeUtil {

	/**
	 * list转树
	 * @param data
	 * @param rootId
	 * @param idFunction
	 * @param parentIdFunction
	 * @param setChildFunction
	 * @return List<E>
	 **/
	public static <T, E> List<E> build(List<E> data, T rootId, Function<E, T> idFunction,
			Function<E, T> parentIdFunction, BiConsumer<E, List<E>> setChildFunction) {
		if (data == null) {
			return null;
		}
		List<E> result = new ArrayList<>();
		data.forEach(node -> {
			if (Objects.equals(rootId, parentIdFunction.apply(node))) {
				result.add(node);
				setChildFunction.accept(node,
						build(data, idFunction.apply(node), idFunction, parentIdFunction, setChildFunction));
			}
		});
		return result;
	}

}

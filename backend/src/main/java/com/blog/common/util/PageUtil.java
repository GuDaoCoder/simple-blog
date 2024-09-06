package com.blog.common.util;

import com.blog.common.base.PageResponse;
import org.springframework.data.domain.Page;

import java.util.function.Function;

/**
 * @author Gudao
 * @since 2024/9/6
 */
public class PageUtil {

	/**
	 * 查询结果转换为分页结果数据
	 * @param page
	 * @param convert
	 * @return PageResponse<Response>
	 **/
	public static <Response, Entity> PageResponse<Response> toResult(Page<Entity> page,
			Function<Entity, Response> convert) {
		PageResponse<Response> pageResponse = new PageResponse<>();
		return pageResponse.setPageNumber(page.getNumber() + 1)
			.setPageSize(page.getSize())
			.setTotal(page.getTotalElements())
			.setItems(page.getContent().stream().map(convert).toList());
	}

}

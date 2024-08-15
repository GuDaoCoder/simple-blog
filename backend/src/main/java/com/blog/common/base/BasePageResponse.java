package com.blog.common.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页出参基类
 *
 * @author Gudao
 * @since 2024/8/2
 */
@Setter
@Getter
public class BasePageResponse<Response> implements Serializable {

	@Serial
	private static final long serialVersionUID = 7162988863813899849L;

	/**
	 * 页码
	 */
	private Long pageNumber;

	/**
	 * 每页数量
	 */
	private Long pageSize;

	/**
	 * 总数
	 */
	private Long total;

	/**
	 * 分页数据
	 */
	private List<Response> items;

}

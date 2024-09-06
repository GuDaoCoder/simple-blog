package com.blog.common.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页出参基类
 *
 * @author Gudao
 * @since 2024/8/2
 */
@Accessors(chain = true)
@Setter
@Getter
public class PageResponse<Response> implements Serializable {

	@Serial
	private static final long serialVersionUID = 7162988863813899849L;

	@Schema(description = "页码")
	private Integer pageNumber;

	@Schema(description = "每页数量")
	private Integer pageSize;

	@Schema(description = "总数")
	private Long total;

	@Schema(description = "分页数据")
	private List<Response> items;

}

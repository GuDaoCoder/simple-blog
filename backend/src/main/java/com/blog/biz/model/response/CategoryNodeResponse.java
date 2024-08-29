package com.blog.biz.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.List;

/**
 * @author Gudao
 * @since 2024/8/27
 */
@Setter
@Getter
public class CategoryNodeResponse extends CategoryResponse {

	@Serial
	private static final long serialVersionUID = -1838066406109346286L;

	@Schema(description = "文章数量")
	private Integer articleCount;

	@Schema(description = "子级数据")
	private List<CategoryNodeResponse> children;

}

package com.blog.biz.model.request;

import com.blog.common.base.BaseRequest;
import com.blog.common.jpa.query.Query;
import com.blog.common.jpa.query.QueryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author Gudao
 * @since 2024/8/27
 */
@Setter
@Getter
public class CategoryQueryRequest extends BaseRequest {

	@Serial
	private static final long serialVersionUID = -4760537724792591681L;

	@Schema(description = "分类名称")
	@Query(type = QueryType.LIKE)
	private String categoryName;

}

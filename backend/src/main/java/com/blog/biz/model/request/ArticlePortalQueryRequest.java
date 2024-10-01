package com.blog.biz.model.request;

import com.blog.common.base.BasePageRequest;
import com.blog.common.jpa.query.Query;
import com.blog.common.jpa.query.QueryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author Gudao
 * @since 2024/10/2
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticlePortalQueryRequest extends BasePageRequest {

	@Serial
	private static final long serialVersionUID = 2391624105991790804L;

	@Schema(description = "所属分类Id")
	@Query(type = QueryType.EQUALS)
	private Long categoryId;

}

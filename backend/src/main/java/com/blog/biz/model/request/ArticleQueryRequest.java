package com.blog.biz.model.request;

import com.blog.biz.enums.ArticleSource;
import com.blog.biz.enums.ArticleStatus;
import com.blog.common.base.BasePageRequest;
import com.blog.common.jpa.query.Query;
import com.blog.common.jpa.query.QueryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author Gudao
 * @since 2024/9/10
 */
@Setter
@Getter
public class ArticleQueryRequest extends BasePageRequest {

	@Serial
	private static final long serialVersionUID = -8985348636437687915L;

	@Schema(description = "文章Id")
	@Query(type = QueryType.EQUALS)
	private Long articleId;

	@Schema(description = "标题")
	@Query(type = QueryType.LIKE)
	private String title;

	@Schema(description = "状态")
	@Query(type = QueryType.EQUALS)
	private ArticleStatus status;

	@Schema(description = "文章来源")
	@Query(type = QueryType.EQUALS)
	private ArticleSource source;

	@Schema(description = "所属分类Id")
	@Query(type = QueryType.EQUALS)
	private Long categoryId;

}

package com.blog.biz.model.request;

import com.blog.common.base.BasePageRequest;
import com.blog.common.jpa.query.Query;
import com.blog.common.jpa.query.QueryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author Gudao
 * @since 2024/8/2
 */
@Setter
@Getter
public class TagQueryRequest extends BasePageRequest {

	@Serial
	private static final long serialVersionUID = 1551861520410882388L;

	@Schema(description = "标签名称")
	@Query(type = QueryType.LIKE)
	private String tagName;

}

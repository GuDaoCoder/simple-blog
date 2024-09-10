package com.blog.biz.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Gudao
 * @since 2024/9/10
 */
@Setter
@Getter
public class ArticleDetailResponse extends ArticleResponse {

	@Schema(description = "标签信息")
	private List<TagResponse> tags;

}

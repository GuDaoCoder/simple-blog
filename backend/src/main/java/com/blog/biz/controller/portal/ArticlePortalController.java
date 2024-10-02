package com.blog.biz.controller.portal;

import com.blog.biz.constant.CommonConstants;
import com.blog.biz.model.request.ArticlePortalQueryRequest;
import com.blog.biz.model.response.ArticleDetailResponse;
import com.blog.biz.service.ArticleService;
import com.blog.common.base.PageResponse;
import com.blog.common.base.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gudao
 * @since 2024/10/2
 */
@Tag(name = "文章管理")
@RestController
@RequestMapping(CommonConstants.REQUEST_PREFIX_PORTAL + "/articles")
@RequiredArgsConstructor
public class ArticlePortalController {

	private final ArticleService articleService;

	@Operation(summary = "门户分页查询文章信息")
	@GetMapping
	public R<PageResponse<ArticleDetailResponse>> portalQuery(ArticlePortalQueryRequest request) {
		return R.success(articleService.portalQuery(request));
	}

	@Operation(summary = "查询文章详情")
	@GetMapping("/{articleId}")
	public R<ArticleDetailResponse> portalGet(
			@Parameter(description = "文章Id") @PathVariable("articleId") Long articleId) {
		return R.success(articleService.portalGetDetail(articleId));
	}

	@Operation(summary = "查询文章内容")
	@GetMapping("/{articleId}/content")
	public R<String> portalGetContent(@Parameter(description = "文章Id") @PathVariable("articleId") Long articleId) {
		return R.success(articleService.getContent(articleId));
	}

}

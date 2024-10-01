package com.blog.biz.controller.admin;

import com.blog.biz.constant.CommonConstants;
import com.blog.biz.model.request.ArticleCoverImageUrlRequest;
import com.blog.biz.model.request.ArticleQueryRequest;
import com.blog.biz.model.response.ArticleDetailResponse;
import com.blog.biz.model.response.ArticleResponse;
import com.blog.biz.service.ArticleService;
import com.blog.biz.sync.ArticleSyncService;
import com.blog.common.base.PageResponse;
import com.blog.common.base.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@Tag(name = "文章管理")
@RestController
@RequestMapping(CommonConstants.REQUEST_PREFIX_ADMIN + "/articles")
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;

	private final ArticleSyncService articleSyncService;

	@Operation(summary = "分页查询文章信息")
	@GetMapping
	public R<PageResponse<ArticleDetailResponse>> query(ArticleQueryRequest request) {
		return R.success(articleService.query(request));
	}

	@Operation(summary = "查询文章内容")
	@GetMapping("/{articleId}/content")
	public R<String> getContent(@Parameter(description = "文章Id") @PathVariable("articleId") Long articleId) {
		return R.success(articleService.getContent(articleId));
	}

	@Operation(summary = "设置文章封面")
	@PatchMapping("/{articleId}/updateCoverImage")
	public R<Void> updateCoverImage(@Parameter(description = "文章Id") @PathVariable("articleId") Long articleId,
			@RequestBody ArticleCoverImageUrlRequest request) {
		articleService.updateCoverImage(articleId, request);
		return R.success();
	}

	@Operation(summary = "发布文章")
	@PatchMapping("/{articleId}/publish")
	public R<Void> publish(@Parameter(description = "文章Id") @PathVariable("articleId") Long articleId) {
		articleService.publish(articleId);
		return R.success();
	}

	@Operation(summary = "下架文章")
	@PatchMapping("/{articleId}/unPublish")
	public R<Void> unPublish(@Parameter(description = "文章Id") @PathVariable("articleId") Long articleId) {
		articleService.unPublish(articleId);
		return R.success();
	}

	@Operation(summary = "同步")
	@GetMapping("/sync")
	public R<Void> sync() {
		articleSyncService.sync();
		return R.success();
	}

}

package com.blog.biz.controller.admin;

import com.blog.biz.constant.CommonConstants;
import com.blog.biz.model.request.TagQueryRequest;
import com.blog.biz.model.response.TagResponse;
import com.blog.biz.service.TagService;
import com.blog.common.base.PageResponse;
import com.blog.common.base.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gudao
 * @since 2024/7/27
 */
@Tag(name = "标签管理")
@RestController
@RequestMapping(CommonConstants.REQUEST_PREFIX_ADMIN + "/tags")
@RequiredArgsConstructor
public class TagController {

	private final TagService tagService;

	@Operation(summary = "分页查询标签信息")
	@GetMapping
	public R<PageResponse<TagResponse>> query(TagQueryRequest request) {
		return R.success(tagService.query(request));
	}

}

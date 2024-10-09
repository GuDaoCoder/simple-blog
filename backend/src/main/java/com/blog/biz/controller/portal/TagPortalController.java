package com.blog.biz.controller.portal;

import com.blog.biz.constant.CommonConstants;
import com.blog.biz.model.response.TagResponse;
import com.blog.biz.service.TagService;
import com.blog.common.base.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Gudao
 * @since 2024/10/9
 */
@Tag(name = "标签管理")
@RestController
@RequestMapping(CommonConstants.REQUEST_PREFIX_PORTAL + "/tags")
@RequiredArgsConstructor
public class TagPortalController {

	private final TagService tagService;

	@Operation(summary = "查询所有标签信息")
	@GetMapping
	public R<List<TagResponse>> queryAll() {
		return R.success(tagService.queryAll());
	}

}

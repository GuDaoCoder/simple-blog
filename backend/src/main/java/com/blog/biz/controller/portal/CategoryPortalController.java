package com.blog.biz.controller.portal;

import com.blog.biz.constant.CommonConstants;
import com.blog.biz.model.response.CategoryNodeResponse;
import com.blog.biz.service.CategoryService;
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
@Tag(name = "分类管理")
@RestController
@RequestMapping(CommonConstants.REQUEST_PREFIX_PORTAL + "/categories")
@RequiredArgsConstructor
public class CategoryPortalController {

	private final CategoryService categoryService;

	@Operation(summary = "查询分类树")
	@GetMapping("/tree")
	public R<List<CategoryNodeResponse>> tree() {
		return R.success(categoryService.portalTree());
	}

}

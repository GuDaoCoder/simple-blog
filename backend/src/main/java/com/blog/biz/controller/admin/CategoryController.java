package com.blog.biz.controller.admin;

import com.blog.biz.constant.CommonConstants;
import com.blog.biz.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@Tag(name = "分类管理")
@RestController
@RequestMapping(CommonConstants.REQUEST_PREFIX_ADMIN + "/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

}

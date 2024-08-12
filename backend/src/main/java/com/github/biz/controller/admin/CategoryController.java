package com.github.biz.controller.admin;

import com.github.biz.constant.CommonConstants;
import com.github.biz.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@RestController
@RequestMapping(CommonConstants.REQUEST_PREFIX_ADMIN + "/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

}

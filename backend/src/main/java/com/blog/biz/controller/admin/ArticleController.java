package com.blog.biz.controller.admin;

import com.blog.biz.constant.CommonConstants;
import com.blog.biz.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@RestController
@RequestMapping(CommonConstants.REQUEST_PREFIX_ADMIN + "/articles")
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;

}

package com.github.biz.controller.admin;

import com.github.biz.constant.CommonConstants;
import com.github.biz.service.ArticleService;
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

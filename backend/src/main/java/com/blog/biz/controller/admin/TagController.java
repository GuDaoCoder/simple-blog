package com.blog.biz.controller.admin;

import com.blog.biz.constant.CommonConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gudao
 * @since 2024/7/27
 */
@Tag(name = "标签")
@RestController
@RequestMapping(CommonConstants.REQUEST_PREFIX_ADMIN + "/tags")
@RequiredArgsConstructor
public class TagController {

}

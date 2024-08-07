package com.github.biz.controller.admin;

import com.github.biz.constant.CommonConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gudao
 * @since 2024/7/27
 */
@RestController
@RequestMapping(CommonConstants.REQUEST_PREFIX_ADMIN + "/tags")
@RequiredArgsConstructor
public class TagController {

}

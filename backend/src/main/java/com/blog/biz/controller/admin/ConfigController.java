package com.blog.biz.controller.admin;

import com.blog.biz.constant.CommonConstants;
import com.blog.biz.model.response.ConfigResponse;
import com.blog.biz.service.ConfigService;
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
 * @since 2024/9/5
 */
@Tag(name = "配置管理")
@RestController
@RequestMapping(CommonConstants.REQUEST_PREFIX_ADMIN + "/configs")
@RequiredArgsConstructor
public class ConfigController {

	private final ConfigService configService;

	@Operation(summary = "查询所有配置信息")
	@GetMapping
	public R<List<ConfigResponse>> list() {
		return R.success(configService.list());
	}

}

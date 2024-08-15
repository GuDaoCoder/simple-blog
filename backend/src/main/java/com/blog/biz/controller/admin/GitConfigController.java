package com.blog.biz.controller.admin;

import com.blog.biz.constant.CommonConstants;
import com.blog.biz.mapper.ConfigMapper;
import com.blog.biz.model.request.GitConfigRequest;
import com.blog.biz.service.ConfigService;
import com.blog.common.base.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gudao
 * @since 2024/8/14
 */
@Tag(name = "Git配置信息")
@RestController
@RequestMapping(CommonConstants.REQUEST_PREFIX_ADMIN + "/gitConfigs")
@RequiredArgsConstructor
public class GitConfigController {

	private final ConfigService configService;

	@Operation(summary = "新增Git配置信息")
	@PostMapping
	public R<Void> add(@RequestBody GitConfigRequest request) {
		configService.save(ConfigMapper.INSTANCE.toGitConfig(request));
		return R.success();
	}

}

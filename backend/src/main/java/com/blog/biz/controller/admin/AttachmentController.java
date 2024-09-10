package com.blog.biz.controller.admin;

import com.blog.biz.constant.CommonConstants;
import com.blog.biz.model.request.AttachmentQueryRequest;
import com.blog.biz.model.response.AttachmentResponse;
import com.blog.biz.service.AttachmentService;
import com.blog.common.base.PageResponse;
import com.blog.common.base.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gudao
 * @since 2024/9/10
 */
@Tag(name = "附件管理")
@RestController
@RequestMapping(CommonConstants.REQUEST_PREFIX_ADMIN + "/attachments")
@RequiredArgsConstructor
public class AttachmentController {

	private final AttachmentService attachmentService;

	@Operation(summary = "分页查询附件信息")
	@GetMapping
	public R<PageResponse<AttachmentResponse>> query(AttachmentQueryRequest request) {
		return R.success(attachmentService.query(request));
	}

}

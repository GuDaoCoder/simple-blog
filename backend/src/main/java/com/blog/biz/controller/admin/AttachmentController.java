package com.blog.biz.controller.admin;

import com.blog.biz.constant.CommonConstants;
import com.blog.biz.file.FileFactory;
import com.blog.biz.model.request.AttachmentQueryRequest;
import com.blog.biz.model.request.AttachmentUploadRequest;
import com.blog.biz.model.response.AttachmentResponse;
import com.blog.biz.model.response.AttachmentUploadResponse;
import com.blog.biz.service.AttachmentService;
import com.blog.common.base.PageResponse;
import com.blog.common.base.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

	@Operation(summary = "上传文件")
	@PostMapping("/upload")
	public R<AttachmentUploadResponse> upload(@Parameter(description = "附件") @RequestPart("file") MultipartFile file,
			AttachmentUploadRequest request) {
		return R.success(attachmentService.upload(file, request));
	}

	@Operation(summary = "下载文件")
	@GetMapping("/download/{fullStorageName}")
	public R<Void> download(@PathVariable("fullStorageName") String fullStorageName,
			HttpServletResponse httpServletResponse) {
		attachmentService.download(fullStorageName, httpServletResponse);
		return R.success();
	}

}

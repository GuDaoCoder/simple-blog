package com.blog.biz.model.request;

import com.blog.biz.enums.AttachmentModule;
import com.blog.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author Gudao
 * @since 2024/9/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AttachmentUploadRequest extends BaseRequest {

	@Serial
	private static final long serialVersionUID = -3858297837107042702L;

	@Schema(description = "所属模块")
	private AttachmentModule module;

}

package com.blog.biz.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Gudao
 * @since 2024/9/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AttachmentUploadResponse extends AttachmentResponse {

	@Schema(description = "访问地址")
	private String url;

}

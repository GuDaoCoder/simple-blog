package com.blog.biz.model.request;

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
public class ArticleCoverImageUrlRequest extends BaseRequest {

	@Serial
	private static final long serialVersionUID = 6723355880989787218L;

	@Schema(description = "封面图片Url")
	private String coverImageUrl;

}

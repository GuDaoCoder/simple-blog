package com.blog.common.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serial;

/**
 * @author Gudao
 * @since 2024/8/27
 */
@Setter
@Getter
public class BasePageRequest extends BaseRequest {

	@Serial
	private static final long serialVersionUID = 6564821226845404262L;

	@Schema(description = "页码")
	private Integer pageNumber = 1;

	@Schema(description = "每页数量")
	private Integer pageSize = 10;

	public Pageable pageable() {
		return PageRequest.of(pageNumber - 1, pageSize);
	}

}

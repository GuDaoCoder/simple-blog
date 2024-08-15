package com.blog.common.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 请求入参实体抽象基类
 *
 * @author Gudao
 * @since 2024/8/1
 */
@Setter
@Getter
public abstract class BaseRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = -484136577781030895L;

	@Schema(description = "请求Id")
	private String requestId;

}

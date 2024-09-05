package com.blog.biz.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author Gudao
 * @since 2024/9/5
 */
@Setter
@Getter
public class ConfigResponse implements Serializable {

	@Serial
	private static final long serialVersionUID = 2819425642922437366L;

	@Schema(description = "配置前缀")
	private String configPrefix;

	@Schema(description = "配置描述")
	private String configDesc;

	@Schema(description = "配置项")
	private List<ConfigItemResponse> items;

}

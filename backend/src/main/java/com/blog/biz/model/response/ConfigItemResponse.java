package com.blog.biz.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Gudao
 * @since 2024/9/5
 */
@Accessors(chain = true)
@Setter
@Getter
public class ConfigItemResponse implements Serializable {

	@Serial
	private static final long serialVersionUID = 3704998945860327243L;

	@Schema(description = "配置项描述")
	private String configItemDesc;

	@Schema(description = "配置项值")
	private String configItemValue;

	@Schema(description = "是否需要加密")
	private boolean encrypt;

	@Schema(description = "是否必须")
	private boolean required;

}

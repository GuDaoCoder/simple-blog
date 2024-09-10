package com.blog.biz.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Gudao
 * @since 2024/8/2
 */
@Setter
@Getter
public class TagResponse implements Serializable {

	@Serial
	private static final long serialVersionUID = -1913806770602840312L;

	@Schema(description = "标签Id")
	private Long tagId;

	@Schema(description = "标签名称")
	private String tagName;

	@Schema(description = "颜色")
	private String color;

	@Schema(description = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createTime;

	@Schema(description = "更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime updateTime;

}

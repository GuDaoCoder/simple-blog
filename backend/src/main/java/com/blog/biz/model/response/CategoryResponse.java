package com.blog.biz.model.response;

import com.blog.common.snowflake.SnowflakeIdGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Gudao
 * @since 2024/8/7
 */

@Setter
@Getter
public class CategoryResponse implements Serializable {

	@Serial
	private static final long serialVersionUID = 7736315998297613350L;

	@Schema(description = "分类Id")
	private Long categoryId;

	@Schema(description = "分类名称")
	private String categoryName;

	@Schema(description = "上级分类")
	private Long parentCategoryId;

	@Schema(description = "全路径Id")
	private String fullId;

	@Schema(description = "顺序")
	private Integer orderNo;

	@Schema(description = "级别")
	private Integer level;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

}

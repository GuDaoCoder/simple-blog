package com.blog.biz.model.response;

import com.blog.biz.enums.ArticleSource;
import com.blog.biz.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Gudao
 * @since 2024/9/10
 */
@Setter
@Getter
public class ArticleResponse {

	@Schema(description = "文章Id")
	private Long articleId;

	@Schema(description = "标题")
	private String title;

	@Schema(description = "摘要")
	private String summary;

	@Schema(description = "封面图片链接")
	private String coverImageUrl;

	@Schema(description = "状态")
	private ArticleStatus status;

	@Schema(description = "文章来源")
	private ArticleSource source;

	@Schema(description = "所属分类Id")
	private Long categoryId;

	@Schema(description = "是否置顶")
	private Boolean top;

	@Schema(description = "是否开启评论")
	private Boolean enableComment;

	@Schema(description = "发布时间")
	private LocalDateTime publishTime;

	@Schema(description = "下架时间")
	private LocalDateTime unpublishTime;

	@Schema(description = "文件hash值")
	private String fileHash;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

}

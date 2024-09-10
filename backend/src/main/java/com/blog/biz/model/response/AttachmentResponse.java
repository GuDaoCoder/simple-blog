package com.blog.biz.model.response;

import com.blog.biz.enums.AttachmentModule;
import com.blog.biz.enums.StoragePolicy;
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
public class AttachmentResponse {

	@Schema(description = "附件id")
	private Long attachmentId;

	@Schema(description = "文件原名称")
	private String originalName;

	@Schema(description = "附件存储名称")
	private String storageName;

	@Schema(description = "扩展名")
	private String extension;

	@Schema(description = "附件路径")
	private String path;

	@Schema(description = "文件大小")
	private Long size;

	@Schema(description = "存储策略")
	private StoragePolicy storagePolicy;

	@Schema(description = "文件hash值")
	private String fileHash;

	@Schema(description = "所属模块")
	private AttachmentModule module;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

}

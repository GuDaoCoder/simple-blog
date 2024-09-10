package com.blog.biz.model.request;

import com.blog.biz.enums.AttachmentModule;
import com.blog.biz.enums.StoragePolicy;
import com.blog.common.base.BasePageRequest;
import com.blog.common.jpa.query.Query;
import com.blog.common.jpa.query.QueryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author Gudao
 * @since 2024/9/10
 */
@Setter
@Getter
public class AttachmentQueryRequest extends BasePageRequest {

	@Serial
	private static final long serialVersionUID = -3095539196669315642L;

	@Schema(description = "附件id")
	@Query(type = QueryType.EQUALS)
	private Long attachmentId;

	@Schema(description = "文件原名称")
	@Query(type = QueryType.LIKE)
	private String originalName;

	@Schema(description = "附件存储名称")
	@Query(type = QueryType.LIKE)
	private String storageName;

	@Schema(description = "存储策略")
	@Query(type = QueryType.EQUALS)
	private StoragePolicy storagePolicy;

	@Schema(description = "所属模块")
	@Query(type = QueryType.EQUALS)
	private AttachmentModule module;

}

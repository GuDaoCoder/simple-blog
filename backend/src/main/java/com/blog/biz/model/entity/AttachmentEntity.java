package com.blog.biz.model.entity;

import com.blog.biz.enums.AttachmentModule;
import com.blog.biz.enums.StoragePolicy;
import com.blog.common.base.BaseEntity;
import com.blog.common.snowflake.SnowflakeIdGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;

/**
 * 附件信息
 *
 * @author Gudao
 * @since 2024/8/16
 */
@Accessors(chain = true)
@Setter
@Getter
@Entity
@Table(name = "T_ATTACHMENT", indexes = { @Index(name = "idx_attachment_storage_name", columnList = "storageName") })
public class AttachmentEntity extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 7938876144941340076L;

	/**
	 * 附件id
	 */
	@Id
	@GeneratedValue(generator = "snowflakeIdGenerator", strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "snowflakeIdGenerator", type = SnowflakeIdGenerator.class)
	private Long attachmentId;

	/**
	 * 文件原名称
	 */
	@Column(length = 32, nullable = false)
	private String originalName;

	/**
	 * 附件存储名称
	 */
	@Column(length = 32, nullable = false)
	private String storageName;

	/**
	 * 扩展名
	 */
	@Column(length = 20, nullable = false)
	private String extension;

	/**
	 * 附件路径
	 */
	@Column(length = 256, nullable = false)
	private String path;

	/**
	 * 文件大小
	 */
	@Column(nullable = false)
	private Long size;

	/**
	 * 存储策略
	 */
	@Column(length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	private StoragePolicy storagePolicy;

	/**
	 * 文件hash值
	 */
	@Column(length = 32)
	private String fileHash;

	/**
	 * 所属模块
	 */
	@Column(length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	private AttachmentModule module;

}

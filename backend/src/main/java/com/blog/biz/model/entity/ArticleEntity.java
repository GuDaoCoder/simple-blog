package com.blog.biz.model.entity;

import com.blog.biz.enums.ArticleSource;
import com.blog.biz.enums.ArticleStatus;
import com.blog.common.base.BaseEntity;
import com.blog.common.snowflake.SnowflakeIdGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@Accessors(chain = true)
@Setter
@Getter
@Entity
@Table(name = "T_ARTICLE", uniqueConstraints = @UniqueConstraint(columnNames = { "title" }))
public class ArticleEntity extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 5659718167753706006L;

	/**
	 * 文章Id
	 */
	@Id
	@GeneratedValue(generator = "snowflakeIdGenerator", strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "snowflakeIdGenerator", type = SnowflakeIdGenerator.class)
	private Long articleId;

	/**
	 * 标题
	 */
	@Column(length = 64, nullable = false)
	private String title;

	/**
	 * 摘要
	 */
	@Column(length = 256)
	private String summary;

	/**
	 * 封面图片链接
	 */
	@Column(length = 256)
	private String coverImageUrl;

	/**
	 * 状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private ArticleStatus status;

	/**
	 * 文章来源
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private ArticleSource source;

	/**
	 * 所属分类Id
	 */
	private Long categoryId;

	/**
	 * 是否置顶
	 */
	@Column(nullable = false)
	private Boolean top;

	/**
	 * 是否开启评论
	 */
	@Column(nullable = false)
	private Boolean enableComment;

	/**
	 * 发布时间
	 */
	private LocalDateTime publishTime;

	/**
	 * 下架时间
	 */
	private LocalDateTime unpublishTime;

	/**
	 * 文件hash值
	 */
	@Column(length = 32)
	private String fileHash;

}

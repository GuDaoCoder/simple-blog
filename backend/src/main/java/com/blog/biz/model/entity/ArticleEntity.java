package com.blog.biz.model.entity;

import com.blog.biz.enums.PostSource;
import com.blog.biz.enums.PostStatus;
import com.blog.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author Gudao
 * @since 2024/8/12
 */
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
	private String coverPictureUrl;

	/**
	 * 状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private PostStatus status;

	/**
	 * 文章来源
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private PostSource source;

	/**
	 * 所属分类Id
	 */
	@Column(nullable = false)
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

}

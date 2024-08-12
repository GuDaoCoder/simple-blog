package com.github.biz.model.entity;

import com.github.biz.enums.PostSource;
import com.github.biz.enums.PostStatus;
import com.github.common.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "T_ARTICLE")
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
	private String title;

	/**
	 * 摘要
	 */
	private String summary;

	/**
	 * 封面图片链接
	 */
	private String coverPictureUrl;

	/**
	 * 状态
	 */
	private PostStatus status;

	/**
	 * 文章来源
	 */
	private PostSource source;

	/**
	 * 所属分类Id
	 */
	private Long categoryId;

	/**
	 * 是否置顶
	 */
	private Boolean top;

	/**
	 * 是否开启评论
	 */
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

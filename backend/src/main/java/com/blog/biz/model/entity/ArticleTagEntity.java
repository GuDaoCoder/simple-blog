package com.blog.biz.model.entity;

import com.blog.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@Setter
@Getter
@Entity
@Table(name = "T_ARTICLE_TAG")
public class ArticleTagEntity extends BaseEntity {

	@Serial
	private static final long serialVersionUID = -3315194942515716271L;

	/**
	 * 主键
	 */
	@Id
	private Long articleTagId;

	/**
	 * 文章Id
	 */
	@Column(nullable = false)
	private Long articleId;

	/**
	 * 标签Id
	 */
	@Column(nullable = false)
	private Long tagId;

}

package com.blog.biz.model.entity;

import com.blog.common.base.BaseEntity;
import jakarta.persistence.*;
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
@Table(name = "T_ARTICLE_CONTENT")
public class ArticleContentEntity extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 6459600440491801060L;

	/**
	 * 主键Id
	 */
	@Id
	private Long articleContentId;

	/**
	 * 文章Id
	 */
	@Column(nullable = false)
	private Long postId;

	/**
	 * 文章内容
	 */
	@Lob
	private String content;

}

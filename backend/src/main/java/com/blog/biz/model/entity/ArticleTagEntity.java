package com.blog.biz.model.entity;

import com.blog.common.base.BaseEntity;
import com.blog.common.snowflake.SnowflakeIdGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.util.Objects;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@Accessors(chain = true)
@Setter
@Getter
@Entity
@Table(name = "T_ARTICLE_TAG", indexes = { @Index(name = "idx_article_tag_article_id", columnList = "articleId") })
public class ArticleTagEntity extends BaseEntity {

	@Serial
	private static final long serialVersionUID = -3315194942515716271L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(generator = "snowflakeIdGenerator", strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "snowflakeIdGenerator", type = SnowflakeIdGenerator.class)
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ArticleTagEntity that = (ArticleTagEntity) o;
		return Objects.equals(articleId, that.articleId) && Objects.equals(tagId, that.tagId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(articleId, tagId);
	}

}

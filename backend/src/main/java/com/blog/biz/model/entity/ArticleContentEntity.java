package com.blog.biz.model.entity;

import com.blog.common.base.BaseEntity;
import com.blog.common.snowflake.SnowflakeIdGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@Accessors(chain = true)
@Setter
@Getter
@Entity
@Table(name = "T_ARTICLE_CONTENT",
		indexes = { @Index(name = "idx_article_content_article_id", columnList = "articleId") })
public class ArticleContentEntity extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 6459600440491801060L;

	/**
	 * 主键Id
	 */
	@Id
	@GeneratedValue(generator = "snowflakeIdGenerator", strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "snowflakeIdGenerator", type = SnowflakeIdGenerator.class)
	private Long articleContentId;

	/**
	 * 文章Id
	 */
	@Column(nullable = false)
	private Long articleId;

	/**
	 * 文章内容
	 */
	@Lob
	private String content;

}

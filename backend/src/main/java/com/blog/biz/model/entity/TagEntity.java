package com.blog.biz.model.entity;

import com.blog.common.base.BaseEntity;
import com.blog.common.snowflake.SnowflakeIdGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;

/**
 * 标签数据库实体
 *
 * @author Gudao
 * @since 2024/8/2
 */
@Accessors(chain = true)
@Setter
@Getter
@Entity
@Table(name = "T_TAG", uniqueConstraints = @UniqueConstraint(columnNames = "tagName"))
public class TagEntity extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 5214190302074473126L;

	/**
	 * 标签Id
	 */
	@Id
	@GeneratedValue(generator = "snowflakeIdGenerator", strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "snowflakeIdGenerator", type = SnowflakeIdGenerator.class)
	private Long tagId;

	/**
	 * 标签名称
	 */
	@Column(length = 64, nullable = false)
	private String tagName;

	/**
	 * 颜色
	 */
	@Column(length = 20, nullable = false)
	private String color;

}

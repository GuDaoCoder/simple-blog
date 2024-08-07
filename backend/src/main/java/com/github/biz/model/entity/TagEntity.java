package com.github.biz.model.entity;

import com.github.common.base.BaseEntity;
import com.github.common.snowflake.SnowflakeIdGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;

/**
 * 标签数据库实体
 *
 * @author Gudao
 * @since 2024/8/2
 */
@Setter
@Getter
@Entity
@Table(name = "T_TAG")
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
	private String tagName;

	/**
	 * 颜色
	 */
	private String color;

}

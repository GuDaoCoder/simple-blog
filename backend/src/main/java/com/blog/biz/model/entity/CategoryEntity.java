package com.blog.biz.model.entity;

import com.blog.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author Gudao
 * @since 2024/8/7
 */
@Setter
@Getter
@Entity
@Table(name = "T_CATEGORY", uniqueConstraints = @UniqueConstraint(columnNames = { "categoryName", "level" }))
public class CategoryEntity extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 6708825045895773484L;

	/**
	 * 分类Id
	 */
	@Id
	private Long categoryId;

	/**
	 * 分类名称
	 */
	@Column(length = 64, nullable = false)
	private String categoryName;

	/**
	 * 上级分类
	 */
	@Column(nullable = false)
	private Long parentCategoryId;

	/**
	 * 全路径Id
	 */
	@Column(length = 256, nullable = false)
	private String fullId;

	/**
	 * 顺序
	 */
	@Column(nullable = false)
	private Integer orderNo;

	/**
	 * 级别
	 */
	@Column(nullable = false)
	private Integer level;

}

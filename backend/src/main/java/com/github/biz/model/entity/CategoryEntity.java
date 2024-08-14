package com.github.biz.model.entity;

import com.github.common.base.BaseEntity;
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
@Table(name = "T_CATEGORY",uniqueConstraints = @UniqueConstraint(columnNames = {"categoryName","level"}))
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
	@Column(length = 64)
	private String categoryName;

	/**
	 * 上级分类
	 */
	private Long parentCategoryId;

	/**
	 * 全路径Id
	 */
	@Column(length = 256)
	private String fullId;

	/**
	 * 顺序
	 */
	private Integer orderNo;

	/**
	 * 级别
	 */
	private Integer level;

}
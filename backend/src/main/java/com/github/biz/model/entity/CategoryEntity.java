package com.github.biz.model.entity;

import com.github.common.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "T_CATEGORY")
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
	private String categoryName;

	/**
	 * 上级分类
	 */
	private Long parentCategoryId;

	/**
	 * 全路径Id
	 */
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

package com.blog.biz.model.entity;

import com.blog.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

/**
 * @author Gudao
 * @since 2024/8/13
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "T_CONFIG")
public class ConfigEntity extends BaseEntity {

	@Serial
	private static final long serialVersionUID = -6248600624723723536L;

	/**
	 * key
	 */
	@Id
	@Column(length = 128)
	private String configKey;

	/**
	 * value
	 */
	@Column(length = 512)
	private String configValue;

}

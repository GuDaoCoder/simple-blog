package com.github.common.base;

import com.github.common.context.UserDetailContext;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据库实体对象抽象基类
 */
@Setter
@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = -3102393059220666166L;

	/**
	 * 创建用户Id
	 */
	private Long createBy;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 更新用户Id
	 */
	private Long updateBy;

	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;

	@PrePersist
	public void prePersist() {
		if (createTime == null) {
			createTime = LocalDateTime.now();
		}
		if (updateTime == null) {
			updateTime = LocalDateTime.now();
		}
		if (createBy == null) {
			createBy = UserDetailContext.get().getUserId();
		}
		if (updateBy == null) {
			updateBy = UserDetailContext.get().getUserId();
		}
	}

	@PreUpdate
	public void preUpdate() {
		if (createTime == null) {
			updateTime = LocalDateTime.now();
		}
		if (updateBy == null) {
			updateBy = UserDetailContext.get().getUserId();
		}
	}

}

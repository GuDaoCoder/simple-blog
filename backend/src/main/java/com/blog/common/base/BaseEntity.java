package com.blog.common.base;

import com.blog.common.context.UserDetailContext;
import com.blog.common.util.DateTimeUtil;
import jakarta.persistence.Column;
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
	@Column(nullable = false)
	private Long createBy;

	/**
	 * 创建时间
	 */
	@Column(nullable = false)
	private LocalDateTime createTime;

	/**
	 * 更新用户Id
	 */
	@Column(nullable = false)
	private Long updateBy;

	/**
	 * 更新时间
	 */
	@Column(nullable = false)
	private LocalDateTime updateTime;

	@PrePersist
	public void prePersist() {
		LocalDateTime now = DateTimeUtil.now();
		if (createTime == null) {
			createTime = now;
		}
		if (updateTime == null) {
			updateTime = now;
		}
		if (createBy == null) {
			createBy = UserDetailContext.getUserId();
		}
		if (updateBy == null) {
			updateBy = UserDetailContext.getUserId();
		}
	}

	@PreUpdate
	public void preUpdate() {
		if (createTime == null) {
			updateTime = LocalDateTime.now();
		}
		if (updateBy == null) {
			updateBy = UserDetailContext.getUserId();
		}
	}

}

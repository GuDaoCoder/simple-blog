package com.blog.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 附件所属模块
 *
 * @author Gudao
 * @since 2024/9/10
 */
@AllArgsConstructor
@Getter
public enum AttachmentModule {

	PICTURE_BED("图床"),

	COVER_PICTURE("封面"),;

	private final String label;

}

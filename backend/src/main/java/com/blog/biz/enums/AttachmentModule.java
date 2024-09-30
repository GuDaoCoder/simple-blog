package com.blog.biz.enums;

import com.blog.biz.model.config.LocalStoragePolicyConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

/**
 * 附件所属模块
 *
 * @author Gudao
 * @since 2024/9/10
 */
@AllArgsConstructor
@Getter
public enum AttachmentModule {

	IMAGE_BED("图床", LocalStoragePolicyConfig::getFullMarkdownImagePath),

	COVER_IMAGE("封面", LocalStoragePolicyConfig::getFullCoverImagePath),;

	private final String label;

	private final Function<LocalStoragePolicyConfig, String> localPathSupplier;

}

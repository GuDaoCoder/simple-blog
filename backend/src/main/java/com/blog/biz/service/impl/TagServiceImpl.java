package com.blog.biz.service.impl;

import com.blog.biz.model.entity.TagEntity;
import com.blog.biz.repository.TagRepository;
import com.blog.biz.service.TagService;
import com.blog.common.util.ColorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Gudao
 * @since 2024/8/5
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

	private final TagRepository tagRepository;

	@Override
	public Long addTag(String tagName) {
		TagEntity tagEntity = new TagEntity();
		tagEntity.setTagName(tagName);
		tagEntity.setColor(ColorUtil.generateHexColor());
		tagRepository.save(tagEntity);
		return tagEntity.getTagId();
	}

}

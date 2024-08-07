package com.github.biz.service;

import com.github.biz.model.entity.TagEntity;
import com.github.biz.repository.TagRepository;
import com.github.common.util.ColorUtil;
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
		tagEntity.setVersion(1);
		tagRepository.save(tagEntity);
		return tagEntity.getTagId();
	}

}

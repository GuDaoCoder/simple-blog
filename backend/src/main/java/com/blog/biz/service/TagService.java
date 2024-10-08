package com.blog.biz.service;

import com.blog.biz.model.request.TagQueryRequest;
import com.blog.biz.model.response.TagResponse;
import com.blog.common.base.PageResponse;

import java.util.List;

/**
 * @author Gudao
 * @since 2024/8/5
 */
public interface TagService {

	/**
	 * 分页查询标签信息
	 * @param request
	 * @return PageResponse<TagResponse>
	 **/
	PageResponse<TagResponse> query(TagQueryRequest request);

	/**
	 * 查询所有标签信息
	 * @param
	 * @return List<TagResponse>
	 **/
	List<TagResponse> queryAll();

}

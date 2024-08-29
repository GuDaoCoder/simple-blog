package com.blog.biz.service;

import com.blog.biz.model.request.CategoryQueryRequest;
import com.blog.biz.model.response.CategoryNodeResponse;

import java.util.List;

/**
 * @author Gudao
 * @since 2024/8/7
 */
public interface CategoryService {

	/**
	 * 查询分类树结构
	 * @param request
	 * @return List<CategoryNodeResponse>
	 **/
	List<CategoryNodeResponse> tree(CategoryQueryRequest request);

}

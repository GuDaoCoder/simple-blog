package com.blog.biz.service;

import com.blog.biz.model.request.ArticleQueryRequest;
import com.blog.biz.model.response.ArticleResponse;
import com.blog.common.base.PageResponse;

/**
 * @author Gudao
 * @since 2024/8/12
 */
public interface ArticleService {

	/**
	 * 分页查询文章信息
	 * @param request
	 * @return PageResponse<ArticleResponse>
	 **/
	PageResponse<ArticleResponse> query(ArticleQueryRequest request);

	/**
	 * 查询文章内容
	 * @param articleId
	 * @return String
	 **/
	String getContent(Long articleId);

}

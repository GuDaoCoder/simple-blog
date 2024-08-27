package com.blog.biz.repository;

import com.blog.biz.model.entity.ArticleContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@Repository
public interface ArticleContentRepository
		extends JpaRepository<ArticleContentEntity, Long>, QuerydslPredicateExecutor<ArticleContentEntity> {

	/**
	 * 根据article查询单条数据
	 * @param articleId
	 * @return ArticleContentEntity
	 **/
	ArticleContentEntity findByArticleId(Long articleId);

}

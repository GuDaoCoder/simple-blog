package com.blog.biz.repository;

import com.blog.biz.model.entity.ArticleTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@Repository
public interface ArticleTagRepository
		extends JpaRepository<ArticleTagEntity, Long>, QuerydslPredicateExecutor<ArticleTagEntity> {

	/**
	 * 根据文章id查询文章标签关联关系
	 * @param articleId
	 * @return List<ArticleTagEntity>
	 **/
	List<ArticleTagEntity> findAllByArticleId(Long articleId);

	/**
	 * 根据多个文章id查询文章标签关联关系
	 * @param articleIds
	 * @return List<ArticleTagEntity>
	 **/
	List<ArticleTagEntity> findAllByArticleIdIn(Collection<Long> articleIds);

}

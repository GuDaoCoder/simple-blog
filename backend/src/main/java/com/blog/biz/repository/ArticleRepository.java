package com.blog.biz.repository;

import com.blog.biz.model.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@Repository
public interface ArticleRepository
		extends JpaRepository<ArticleEntity, Long>, QuerydslPredicateExecutor<ArticleEntity> {

	ArticleEntity findByTitle(String title);

	@Query(value = "select category_id,count(*) as article_count from t_article where category_id in (:categoryIds) group by category_id",
			nativeQuery = true)
	List<Object[]> findCountByCategoryId(@Param("categoryIds") Set<Long> categoryIds);

}

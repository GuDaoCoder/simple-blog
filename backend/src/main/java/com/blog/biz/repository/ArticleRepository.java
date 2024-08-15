package com.blog.biz.repository;

import com.blog.biz.model.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Gudao
 * @since 2024/8/12
 */
@Repository
public interface ArticleRepository
		extends JpaRepository<ArticleEntity, Long>, QuerydslPredicateExecutor<ArticleEntity> {

}

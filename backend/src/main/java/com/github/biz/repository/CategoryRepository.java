package com.github.biz.repository;

import com.github.biz.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Gudao
 * @since 2024/8/7
 */
@Repository
public interface CategoryRepository
		extends JpaRepository<CategoryEntity, Long>, QuerydslPredicateExecutor<CategoryEntity> {

}

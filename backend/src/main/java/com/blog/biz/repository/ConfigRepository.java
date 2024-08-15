package com.blog.biz.repository;

import com.blog.biz.model.entity.ConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Gudao
 * @since 2024/8/13
 */
@Repository
public interface ConfigRepository extends JpaRepository<ConfigEntity, Long>, QuerydslPredicateExecutor<ConfigEntity> {

	List<ConfigEntity> findAllByConfigKeyLike(String configKeyPrefix);

}

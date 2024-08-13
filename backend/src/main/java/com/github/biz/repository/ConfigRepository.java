package com.github.biz.repository;

import com.github.biz.model.entity.ConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Gudao
 * @since 2024/8/13
 */
@Repository
public interface ConfigRepository extends JpaRepository<ConfigEntity, Long>, QuerydslPredicateExecutor<ConfigEntity> {

}

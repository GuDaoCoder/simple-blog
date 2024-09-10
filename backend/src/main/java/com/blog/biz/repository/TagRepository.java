package com.blog.biz.repository;

import com.blog.biz.model.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long>, QuerydslPredicateExecutor<TagEntity>,
		JpaSpecificationExecutor<TagEntity> {

}

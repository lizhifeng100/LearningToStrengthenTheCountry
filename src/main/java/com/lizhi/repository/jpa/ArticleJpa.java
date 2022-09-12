package com.lizhi.repository.jpa;

import com.lizhi.entity.ArticleUrlPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author: 荔枝
 * @date: 2022/9/12 16 26
 * @description:
 */
@Repository
public interface ArticleJpa extends JpaRepository<ArticleUrlPO, Long>, JpaSpecificationExecutor<Long> {
}

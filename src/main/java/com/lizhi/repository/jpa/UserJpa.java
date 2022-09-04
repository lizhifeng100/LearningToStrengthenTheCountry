package com.lizhi.repository.jpa;

import com.lizhi.entity.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author lizhi
 */
@Repository
public interface UserJpa extends JpaRepository<UserPO, Long>, JpaSpecificationExecutor<Long> {
}

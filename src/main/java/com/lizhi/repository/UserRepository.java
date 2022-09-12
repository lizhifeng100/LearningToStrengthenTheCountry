package com.lizhi.repository;

import com.lizhi.entity.UserPO;

import java.time.LocalDateTime;

/**
 * @author: 荔枝
 * @date: 2022/9/4 19 22
 * @description:
 */
public interface UserRepository {

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @param now  现在时间
     */
    void saveUser(UserPO user, LocalDateTime now);
}

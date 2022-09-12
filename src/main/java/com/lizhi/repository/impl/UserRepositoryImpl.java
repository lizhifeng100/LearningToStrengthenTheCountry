package com.lizhi.repository.impl;

import com.lizhi.common.Constants;
import com.lizhi.entity.UserPO;
import com.lizhi.repository.UserRepository;
import com.lizhi.repository.jpa.UserJpa;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author: 荔枝
 * @date: 2022/9/4 19 22
 * @description:
 */
@Service
public class UserRepositoryImpl implements UserRepository {

    @Resource
    private UserJpa userJpa;


    @Override
    public void saveUser(UserPO user , LocalDateTime now) {
        user.setCreateTime(now);
        user.setIsDel(Constants.NO_DEL);
        user.setModifyTime(now);
        userJpa.save(user);
    }
}

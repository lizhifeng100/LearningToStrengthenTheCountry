package com.lizhi.repository;

/**
 * @author: 荔枝
 * @date: 2022/9/4 19 22
 * @description:
 */
public interface UserRepository {

    /**
     * 保存用户信息
     *
     * @param sign   sign
     * @param prData prData
     */
    void saveUser(String sign, String prData ,String QRCode);
}

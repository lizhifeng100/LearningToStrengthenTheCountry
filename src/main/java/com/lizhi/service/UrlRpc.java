package com.lizhi.service;

import com.lizhi.DO.ScoreDO;
import com.lizhi.entity.UserPO;

import java.util.Map;

/**
 * @author: 荔枝
 * @date: 2022/9/3 22 39
 * @description:
 */
public interface UrlRpc {

    /**
     * 获取sign
     *
     * @return 返回sign
     */
    String getSign();

    /**
     * 获取qrdata，用于刷新二维码
     *
     * @return qrdata
     */
    String getQrData();

    /**
     * 确认登陆是否成功
     *
     * @param qrData qrData
     * @return secret
     */
    String confirmLogin(String qrData);

    /**
     * 检测登陆安全，获取token
     *
     * @param sign   sign
     * @param secret secret
     * @return 安全登陆
     */
    Map<String, String> secureCheck(String sign, String secret);

    /**
     * 获取名字和uid
     *
     * @param token token
     * @return userPo
     */
    UserPO getUidAndName(String token);

    /**
     * 获取总积分
     *
     * @return 总积分
     */
    ScoreDO totalScore();

    /**
     * 每天的积分
     *
     * @return 每天积分
     */
    ScoreDO dailyScore();


    void readArticle();

}

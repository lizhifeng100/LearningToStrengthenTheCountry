package com.lizhi.service;

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
     * @param qrCode q
     * @return secret
     */
    String confirmLogin(String qrCode);

    /**
     * 检测登陆安全
     *
     * @param sign   sign
     * @param secret secret
     * @return 安全登陆
     */
    Boolean secureCheck(String sign, String secret);
}

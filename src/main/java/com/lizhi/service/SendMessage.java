package com.lizhi.service;

import java.util.Map;

/**
 * @author lizhi
 * 发送消息模块
 */
public interface SendMessage {

    /**
     * 发送消息
     *
     * @param msg 消息内容
     * @param key 秘钥
     * @return 返回pushId和readKey
     */
    Map<String, String> sendMessageServerChan(String msg, String key);

    /**
     * 验证消息是否发送成功
     *
     * @param pushId  pushid
     * @param readKey readKey
     * @return 是否推送成功
     */
    Boolean confirmPush(String pushId, String readKey);
}

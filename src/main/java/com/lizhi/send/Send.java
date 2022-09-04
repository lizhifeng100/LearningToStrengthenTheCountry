package com.lizhi.send;

import com.lizhi.common.Constants;
import com.lizhi.common.Wait;
import com.lizhi.service.SendMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: 荔枝
 * @date: 2022/9/4 20 30
 * @description:
 */
@Service
public class Send {

    public static final int DEFAULT_TIMES = 3;

    @Resource
    private SendMessage sendMessage;

    /**
     * 发送消息，如果失败会重复三次。
     *
     * @param msg 消息内容
     * @param key 密匙
     */
    public void send(String msg, String key) {
        for (int i = 0; i < DEFAULT_TIMES; i++) {
            Map<String, String> map = sendMessage.sendMessageServerChan(msg, key);
            Wait.waitSeconds(5L);
            Boolean isOk = false;
            for (int j = 0; j < DEFAULT_TIMES; j++) {
                isOk = sendMessage.confirmPush(map.get(Constants.PUSH_ID), map.get(Constants.READ_KEY));
                if (Boolean.TRUE.equals(isOk)) {
                    break;
                }
                Wait.waitSeconds(30L);
            }
            if (Boolean.TRUE.equals(isOk)) {
                break;
            }
        }
    }
}

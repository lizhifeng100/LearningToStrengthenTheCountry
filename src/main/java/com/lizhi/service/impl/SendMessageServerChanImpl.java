package com.lizhi.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.lizhi.common.Constants;
import com.lizhi.common.UrlConstants;
import com.lizhi.myException.MyException;
import com.lizhi.service.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 荔枝
 * @date: 2022/9/4 19 40
 * @description:
 */
@Service
public class SendMessageServerChanImpl implements SendMessage {

    private static final Logger logger = LoggerFactory.getLogger(SendMessageServerChanImpl.class);

    @Resource
    private RestTemplate restTemplate;

    @Override
    public Map<String, String> sendMessageServerChan(String msg, String key) {
        String url = UrlConstants.SERVER_CHAN + key + ".send";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("title", "二维码有效期5分钟");
        map.add("desp", "![avatar][base64str]\n[base64str]:" + msg);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        try {
            JSONObject response = restTemplate.postForObject(url, request, JSONObject.class);
            assert response != null;
            logger.info("server酱发送消息：{}", response);
            JSONObject data = response.getJSONObject("data");
            String pushId = data.getString("pushid");
            String readKey = data.getString("readkey");
            Map<String, String> result = new HashMap<>();
            result.put(Constants.PUSH_ID, pushId);
            result.put(Constants.READ_KEY, readKey);
            return result;
        } catch (Exception e) {
            throw new MyException("发送消息出错", e);
        }
    }

    @Override
    public Boolean confirmPush(String pushId, String readKey) {
        String url = UrlConstants.SERVER_CHAN_PUSH + "?id=" + pushId + "&readkey=" + readKey;
        try {
            JSONObject result = restTemplate.getForObject(url, JSONObject.class);
            assert result != null;
            logger.info("server酱发送消息回调:{}", result);
            String isOk = result.getJSONObject("data").getJSONObject("wxstatus").getString("errmsg");
            if (Constants.OK.equals(isOk)) {
                return true;
            }
        } catch (Exception e) {
            throw new MyException("确认消息发送失败", e);
        }
        return false;
    }
}

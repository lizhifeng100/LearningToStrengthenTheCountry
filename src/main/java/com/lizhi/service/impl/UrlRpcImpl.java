package com.lizhi.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.lizhi.common.UrlConstants;
import com.lizhi.common.Wait;
import com.lizhi.myException.MyException;
import com.lizhi.service.UrlRpc;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: 荔枝
 * @date: 2022/9/3 22 40
 * @description:
 */
@Service
public class UrlRpcImpl implements UrlRpc {

    private static final int defaultTimes = 40;

    private static final Logger logger = LoggerFactory.getLogger(UrlRpcImpl.class);

    @Resource
    private RestTemplate restTemplate;

    @Override
    public String getSign() {
        //请求得到的数据：{"data":{"sign":"a46529734fae47f0be8787bb81eb73ce"},"message":"OK","code":200,"ok":true}
        JSONObject signJsonObject;
        try {
            signJsonObject = restTemplate.getForObject(UrlConstants.SIGN_LINK, JSONObject.class);
            assert signJsonObject != null;
            return signJsonObject.getJSONObject("data").getString("sign");
        } catch (Exception e) {
            throw new MyException("获取sign失败", e);
        }
    }

    @Override
    public String getQrData() {
        JSONObject qrJsonObject;
        try {
            //2{"success":true,"result":"qr:D6A8B357-4057-4B92-AF32-0E6F6A841AF0"}
            qrJsonObject = restTemplate.getForObject(UrlConstants.QR_DATA_LINK, JSONObject.class);
            assert qrJsonObject != null;
            return qrJsonObject.getString("result");
        } catch (Exception e) {
            throw new MyException("获取qr_data失败", e);
        }
    }

    @Override
    public String confirmLogin(String qrCode) {
        for (int i = 0; i < defaultTimes; i++) {
            String secret = confirm(qrCode);
            if (StringUtils.isNotEmpty(secret)) {
                return secret;
            }
            Wait.waitSeconds(5L);
        }
        return "";
    }

    @Override
    public Boolean secureCheck(String sign, String secret) {
        Map<String, String> map = new HashMap<>(16);
        map.put("code", secret);
        map.put("state", sign.substring(0, 16) + UUID.randomUUID());
        //+ "&state=" + sign.substring(0, 16)
        String url = UrlConstants.SECURE_CHECK + "?code=" + secret + "&state=" + sign.substring(1, 16) + UUID.randomUUID();
        try {
            ResponseEntity<JSONObject> forObject = restTemplate.getForEntity(url, JSONObject.class);
            JSONObject body = forObject.getBody();
            assert body != null;
            logger.info("安全测试是否通过：{}", body);
            return body.getBoolean("success");
        } catch (Exception e) {
            throw new MyException("安全测试不通过", e);
        }
    }

    private String confirm(String qrCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("qrCode", qrCode);
        map.add("goto", "https://oa.xuexi.cn");
        map.add("pdmToken", "");
        // 组装请求体
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        try {
            JSONObject res = restTemplate.postForObject(UrlConstants.CONFIRM_LINK, request, JSONObject.class);
            logger.info("查看二维码是否扫码成功：{}", res);
            assert res != null;
            Boolean success = res.getBoolean("success");
            if (Boolean.TRUE.equals(success)) {
                return res.getString("data").split("=")[1];
            }
        } catch (Exception e) {
            throw new MyException("获取secret失败", e);
        }
        return "";
    }


}

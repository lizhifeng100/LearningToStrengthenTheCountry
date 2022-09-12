package com.lizhi.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lizhi.DO.ScoreDO;
import com.lizhi.common.Constants;
import com.lizhi.common.UrlConstants;
import com.lizhi.common.Wait;
import com.lizhi.entity.ArticleUrlPO;
import com.lizhi.entity.UserPO;
import com.lizhi.myException.MyException;
import com.lizhi.repository.jpa.ArticleJpa;
import com.lizhi.service.UrlRpc;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: 荔枝
 * @date: 2022/9/3 22 40
 * @description:
 */
@Service
public class UrlRpcImpl implements UrlRpc {

    private static final int DEFAULT_TIMES = 40;

    private static final int SUCCESS_CODE = 200;


    private static final Logger logger = LoggerFactory.getLogger(UrlRpcImpl.class);

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private ArticleJpa articleJpa;

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
        for (int i = 0; i < DEFAULT_TIMES; i++) {
            String secret = confirm(qrCode);
            if (StringUtils.isNotEmpty(secret)) {
                return secret;
            }
            Wait.waitSeconds(5L);
        }
        return "";
    }

    @Override
    public Map<String, String> secureCheck(String sign, String secret) {
        Map<String, String> result = new HashMap<>(16);
        String url = UrlConstants.SECURE_CHECK + "?code=" + secret + "&state=" + sign.substring(1, 16) + UUID.randomUUID();
        try {
            ResponseEntity<JSONObject> forObject = restTemplate.getForEntity(url, JSONObject.class);
            JSONObject body = forObject.getBody();
            assert body != null;
            Boolean success = body.getBoolean("success");
            if (Boolean.TRUE.equals(success)) {
                HttpHeaders headers = forObject.getHeaders();
                List<String> cookies = headers.get("Set-Cookie");
                if (cookies != null && !cookies.isEmpty()) {
                    String[] split = cookies.get(0).split(";");
                    String token = split[0].split("=")[1];
                    String expire = split[1].split("=")[1];
                    result.put(Constants.TOKEN, token);
                    result.put(Constants.EXPIRE, expire);
                }
            }
            return result;
        } catch (Exception e) {
            throw new MyException("安全测试不通过", e);
        }
    }

    @Override
    public UserPO getUidAndName(String token) {
        UserPO user = new UserPO();

        HttpHeaders headers = new HttpHeaders();
        List<String> cookies = new ArrayList<>();
        cookies.add(Constants.TOKEN + "=" + token);
        headers.put(HttpHeaders.COOKIE, cookies);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        try {
            JSONObject result = restTemplate.exchange(UrlConstants.USER_INFO, HttpMethod.GET, requestEntity, JSONObject.class).getBody();
            // result = restTemplate.getForObject(UrlConstants.USER_INFO, JSONObject.class);
            assert result != null;
            Integer code = result.getInteger("code");
            if (code == SUCCESS_CODE) {
                JSONObject data = result.getJSONObject("data");
                Long uid = data.getLong("uid");
                String nick = data.getString("nick");
                user.setUid(uid);
                user.setUserName(nick);
                return user;
            }
        } catch (Exception e) {
            throw new MyException("获取身份信息出错", e);
        }
        return user;
    }

    @Override
    public ScoreDO totalScore() {
        JSONObject result;
        ScoreDO score = new ScoreDO();
        try {
            result = restTemplate.getForObject(UrlConstants.TOTAL_SCORE, JSONObject.class);
            assert result != null;
            Integer code = result.getInteger("code");
            if (code == SUCCESS_CODE) {
                JSONObject data = result.getJSONObject("data");
                Long uid = data.getLong("userId");
                Integer total = data.getInteger("score");
                score.setTotalScore(total);
                score.setUid(uid);
                return score;
            }
        } catch (Exception e) {
            throw new MyException("获取总积分出错", e);
        }

        return score;
    }

    @Override
    public ScoreDO dailyScore() {
        JSONObject result;
        ScoreDO score = new ScoreDO();
        try {
            result = restTemplate.getForObject(UrlConstants.DAILY_SCORE, JSONObject.class);
            assert result != null;
            Integer code = result.getInteger("code");
            if (code == SUCCESS_CODE) {
                JSONObject data = result.getJSONObject("data");
                Long uid = data.getLong("userId");
                Integer totalScore = data.getInteger("totalScore");
                List<JSONObject> taskProgress = data.getList("taskProgress", JSONObject.class);
                score.setArticleScore(taskProgress.get(0).getInteger("currentScore"));
                score.setVideoCountScore(taskProgress.get(1).getInteger("currentScore"));
                score.setWeeklyQuizScore(taskProgress.get(2).getInteger("currentScore"));
                score.setVideoDurationScore(taskProgress.get(3).getInteger("currentScore"));
                score.setLoginScore(taskProgress.get(4).getInteger("currentScore"));
                score.setSpecialQuestionsScore(taskProgress.get(5).getInteger("currentScore"));
                score.setDailyQuizScore(taskProgress.get(6).getInteger("currentScore"));
                score.setDailyTotalScore(totalScore);
                score.setUid(uid);
                return score;
            }
        } catch (Exception e) {
            throw new MyException("获取各项积分出错", e);
        }
        return score;
    }

    @Override
    public void readArticle() {

        String result = restTemplate.getForObject(UrlConstants.ALL_ARTICLE_URL, String.class);
        String pattern = "(list)(.*?)(count)";
        Pattern compile = Pattern.compile(pattern);
        assert result != null;
        Matcher matcher = compile.matcher(result);
        if (matcher.find()) {
            String group = matcher.group(0);
            String temp = group.replace("list\":", "").replace(",\"count", "");
            JSONArray objects = JSON.parseArray(temp);
            List<ArticleUrlPO> articles = new ArrayList<>();
            for (int i = 0; i < objects.size(); i++) {
                JSONObject jsonObject = JSON.parseObject(objects.get(i).toString());
                String frst_name = jsonObject.getString("frst_name");
                String id = jsonObject.getString("_id");
                String url = jsonObject.getString("static_page_url");
                ArticleUrlPO articleUrlPO = new ArticleUrlPO();
                articleUrlPO.setFirstName(frst_name);
                articleUrlPO.setId(id);
                articleUrlPO.setUrl(url);
                articleUrlPO.setIsDel(Constants.NO_DEL);
                LocalDateTime now = LocalDateTime.now();
                articleUrlPO.setCreateTime(now);
                articleUrlPO.setModifyTime(now);
                articles.add(articleUrlPO);
            }
            articleJpa.saveAll(articles);
        }
    }

    private String confirm(String qrCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("qrCode", qrCode);
        map.add("goto", UrlConstants.GO_TO);
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

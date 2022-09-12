package com.lizhi.service.impl;

import com.lizhi.common.Constants;
import com.lizhi.DO.ScoreDO;
import com.lizhi.service.Score;
import com.lizhi.service.UrlRpc;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: 荔枝
 * @date: 2022/9/3 17 01
 * @description:
 */
@Service
public class ScoreImpl implements Score {

    @Resource
    private UrlRpc urlRpc;

    @Override
    public Integer getScore(Long uid, String category) {
        ScoreDO score = urlRpc.dailyScore();
        Integer result = 0;
        if (Constants.ARTICLE.equals(category)) {
            result = score.getArticleScore();
        } else if (Constants.VIDEO_COUNT.equals(category)) {
            result = score.getVideoCountScore();
        } else if (Constants.VIDEO_DURATION.equals(category)) {
            result = score.getVideoDurationScore();
        } else if (Constants.DAILY_QUIZ_SCORE.equals(category)) {
            result = score.getDailyQuizScore();
        } else if (Constants.LOGIN_SCORE.equals(category)) {
            result = score.getLoginScore();
        } else if (Constants.WEEKLY_QUIZ_SCORE.equals(category)) {
            result = score.getWeeklyQuizScore();
        } else if (Constants.SPECIAL_QUESTION_SCORE.equals(category)) {
            result = score.getSpecialQuestionsScore();
        } else if (Constants.TOTAL_SCORE.equals(category)) {
            result = score.getTotalScore();
        } else if (Constants.DAILY_TOTAL_SCORE.equals(category)) {
            result = score.getDailyTotalScore();
        }
        return result;
    }

    @Override
    public ScoreDO pushScore() {
        ScoreDO score = urlRpc.dailyScore();
        score.setTotalScore(urlRpc.totalScore().getTotalScore());
        return score;
    }
}

package com.lizhi.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 荔枝
 * @date: 2022/9/3 17 05
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDO {

    private Long id;

    /**
     * 登陆分数
     */
    private Integer loginScore;

    /**
     * 文章分数
     */
    private Integer articleScore;

    /**
     * 视频次数
     */
    private Integer videoCountScore;

    /**
     * 视频时长
     */
    private Integer videoDurationScore;

    /**
     * 每日答题
     */
    private Integer dailyQuizScore;

    /**
     * 专项答题
     */
    private Integer specialQuestionsScore;

    /**
     * 每周答题
     */
    private Integer weeklyQuizScore;

    private Long uid;

    private Integer totalScore;

    private Integer dailyTotalScore;
}

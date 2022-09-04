package com.lizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: 荔枝
 * @date: 2022/9/3 17 05
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "score")
@Entity
public class ScorePo extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private Long id;

    /**
     * 登陆分数
     */
    @Basic
    @Column(name = "login")
    private Integer loginScore;

    /**
     * 文章分数
     */
    @Basic
    @Column(name = "article")
    private Integer articleScore;

    /**
     * 视频次数
     */
    @Basic
    @Column(name = "video_count")
    private Integer videoCountScore;

    /**
     * 视频时长
     */
    @Basic
    @Column(name = "video_duration")
    private Integer videoDurationScore;

    /**
     * 每日答题
     */
    @Basic
    @Column(name = "daily_quiz")
    private Integer dailyQuizScore;

    /**
     * 专项答题
     */
    @Basic
    @Column(name = "special_questions")
    private Integer specialQuestionsScore;

    /**
     * 每周答题
     */
    @Basic
    @Column(name = "weekly_quiz")
    private Integer weeklyQuizScore;

    /**
     * 用户id
     */
    @Basic
    @Column(name = "user_id")
    private Long userId;
}

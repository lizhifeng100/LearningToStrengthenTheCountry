package com.lizhi.service;

import com.lizhi.DO.ScoreDO;

/**
 * @author 李枝峰
 */
public interface Score {

    /**
     * 获取不同项目的分数或者获取总分数
     *
     * @param category 不同类型的分数
     * @param uid      uid
     * @return 分数
     */
    Integer getScore(Long uid, String category);

    /**
     * 保存分数
     *
     * @return 推送消息到微信
     */
    ScoreDO pushScore();
}

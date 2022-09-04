package com.lizhi.service;

/**
 * @author 李枝峰
 */
public interface Score {

    /**
     * 获取不同项目的分数或者获取总分数
     *
     * @param category 不同类型的分数
     * @return 分数
     */
    Integer getScore(String category);
}

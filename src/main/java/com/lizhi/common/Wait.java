package com.lizhi.common;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: 荔枝
 * @date: 2022/9/4 20 49
 * @description:
 */
public class Wait {


    public static void waitSeconds(Long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

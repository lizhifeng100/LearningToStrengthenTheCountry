package com.lizhi.myException;

import lombok.NoArgsConstructor;

/**
 * @author: 荔枝
 * @date: 2022/9/3 22 49
 * @description:
 */
@NoArgsConstructor
public class MyException extends RuntimeException {
    public MyException(String message, Throwable cause) {
        super(message, cause);
    }
}

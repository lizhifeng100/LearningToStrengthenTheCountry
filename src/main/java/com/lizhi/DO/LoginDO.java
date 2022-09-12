package com.lizhi.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 荔枝
 * @date: 2022/9/12 13 45
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDO {

    private String token;

    private Long expire;


}

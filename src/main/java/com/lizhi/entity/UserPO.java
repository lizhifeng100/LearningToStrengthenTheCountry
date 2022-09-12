package com.lizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author: 荔枝
 * @date: 2022/9/4 18 43
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class UserPO extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * 用户名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 学习强国uid
     */
    @Basic
    @Column(name = "uid")
    private Long uid;

    /**
     * 学习强国的token
     */
    @Basic
    @Column(name = "token")
    private String token;

    @Basic
    @Column(name = "expireTime")
    private LocalDateTime expireTime;

    /**
     * 订阅号的key
     */
    @Basic
    @Column(name = "my_key")
    private String key;

}

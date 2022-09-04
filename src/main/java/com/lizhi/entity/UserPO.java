package com.lizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author: 荔枝
 * @date: 2022/9/4 18 43
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@Entity
public class UserPO extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户名称
     */
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "uid")
    private String uid;
    @Basic
    @Column(name = "sign")
    private String sign;
    @Basic
    @Column(name = "pr_data")
    private String prData;

    @Basic
    @Column(name = "qr_code")
    private String QRCode;

    /**
     * prData 过期时间
     */
    @Basic
    @Column(name = "pr_data_expire")
    private LocalDateTime prDataExpireAt;

    /**
     * prData 创建时间
     */
    @Basic
    @Column(name = "pr_data_create")
    private LocalDateTime prDataCreateAt;

}

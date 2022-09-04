package com.lizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author: 荔枝
 * @date: 2022/9/3 23 35
 * @description:
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@MappedSuperclass
public class BasePO {
    @Basic
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Basic
    @Column(name = "modify_time")
    private LocalDateTime modifyTime;
    @Basic
    @Column(name = "is_del")
    private Short isDel;
}

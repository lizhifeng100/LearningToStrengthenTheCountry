package com.lizhi.entity;

import com.lizhi.entity.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: 荔枝
 * @date: 2022/9/12 16 15
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class ArticleUrlPO extends BasePO {

    @Column
    private String firstName;

    @Id
    private String id;

    @Column
    private String url;


}

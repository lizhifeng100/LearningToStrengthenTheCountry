package com.lizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: 荔枝
 * @date: 2022/9/4 21 04
 * @description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "send_message")
public class SendMessagePO extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sendId;
}

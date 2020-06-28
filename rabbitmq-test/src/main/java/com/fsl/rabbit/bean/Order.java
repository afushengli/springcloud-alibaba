package com.fsl.rabbit.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    private String orderSn;  //订单号
    private Long skuId; //商品Id
    private Integer num; //数量
    private Integer memberId; // 会员Id



}

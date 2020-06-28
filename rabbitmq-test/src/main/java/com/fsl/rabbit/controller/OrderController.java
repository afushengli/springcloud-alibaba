package com.fsl.rabbit.controller;

import com.fsl.rabbit.bean.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OrderController {


    @Autowired
    RabbitTemplate     rabbitTemplate;

    @GetMapping(value="/create/order")
    private String  createOrder(Long skuId,Integer num,Integer  memberId){
        Order order = new Order(UUID.randomUUID().toString().replace("-",""),skuId,num,memberId);
       // rabbitTemplate.convertAndSend("order-exchange","createOrder",order);

        rabbitTemplate.convertAndSend("user.order.delay.exchange","order_delay",order);
        return order.toString();
    }
}

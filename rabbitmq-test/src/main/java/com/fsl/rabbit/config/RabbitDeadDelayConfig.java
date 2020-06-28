package com.fsl.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * 一个延迟队列和一个死信队列演示
 */
@Configuration
public class RabbitDeadDelayConfig {

    @Bean
    public Exchange delayExchange(){
        return new DirectExchange("user.order.delay.exchange",true,false);
    }


    @Bean
    public Queue delayQueue(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl" , 10000); //这个队列里所有消息的存活时间，注意参数要写对了
        arguments.put("x-dead-letter-exchange" , "user.order.exchange"); //消息死了，会把消息交给哪个 交换机
        arguments.put("x-dead-letter-routing-key" , "order"); //私信发送出去的路由键,这个是deadBingding 中的routingKey
        return new Queue("user.order.delay.queue",true,false,false,arguments);
    }


    @Bean
    public Binding delayBinding(){
        return new Binding("user.order.delay.queue", Binding.DestinationType.QUEUE,"user.order.delay.exchange","order_delay",null);
    }


    @Bean
    public Exchange deadExchange(){
        return new DirectExchange("user.order.exchange",true,false);
    }


    @Bean
    public Queue deadQueue(){
        return new Queue("user.order.queue",true,false,false,null);
    }

    @Bean
    public Binding deadBinding(){
        return new Binding("user.order.queue", Binding.DestinationType.QUEUE,"user.order.exchange","order",null);
    }



}

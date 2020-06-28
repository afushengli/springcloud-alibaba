package com.fsl.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class rabbitmqConfig {

    @Bean
    public Queue hellQueue(){
        return new Queue("hellQueue",true,false,false,null);
    }


    @Bean
    public Queue orderQueue(){
        return new Queue("order-queue",true,false,false,null);
    }


    @Bean
    public Exchange exchange(){
        return new DirectExchange("order-exchange",true,false,null);
    }

    @Bean
    public Binding binding(){
        return new Binding("order-queue", Binding.DestinationType.QUEUE,"order-exchange","createOrder",null);
    }
}

package com.fsl.rabbit;


import com.fsl.rabbit.bean.User;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqTestApplicationTests {

    @Autowired
    RabbitTemplate  rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    void contextLoads() {
        User user = new User("张三","2212@qq.com");
      //  rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend("direct_exchange","hello",user);
        System.out.println("发送消息完成");
    }


    @Test
    public void  createQueue(){
        //(String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments)
        Queue queue = new  Queue("myquence-01",true,false,false,null);
        amqpAdmin.declareQueue(queue);
        System.out.println("队列创建完成");

    }


    @Test
    public void  createExcahnge(){
        //DirectExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> arguments) {
        Exchange exchange = new DirectExchange("my-echange",true,false);
       amqpAdmin.declareExchange(exchange);
        System.out.println("交换机创建完成创建完成");
    }


    @Test
    public void  createBiding(){
        // Binding(String destination,  //目的地
        //   Binding.DestinationType destinationType,  //目的地类型  队列或者 交换机呢
        //    String exchange,   //交换机
        //     String routingKey,  //路邮键
        //     @Nullable Map<String, Object> arguments)

        Binding binding   = new Binding("myquence-01", Binding.DestinationType.QUEUE,"my-echange","hello",null);
        amqpAdmin.declareBinding(binding);
        System.out.println("biding创建完成创建完成");
    }








}






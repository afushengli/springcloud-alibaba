package com.fsl.rabbit.service;


import com.fsl.rabbit.bean.Order;
import com.fsl.rabbit.bean.User;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;


/**
 * 消息确认机制
 *  1） 如果这条消息收到了，在处理期间，如果出现了运行时异常，默认为消息没有被正确处理
 *     消息状态unack；队列感知到有一个unack消息，
 *     队列会再次把unack的消息发给其他消费者
 *
 *  2） 我们不要让他认为是ack，还是unack，手动确认机制
 *       场景：
 *          我们收到了消息，比去年各库存扣了，但是出现了位置异常，导致了消息重新入队
 *          这个消息会被不断的重复发给我们
 *     1） 手动ack
 *     2） 接口幂等性，在本地维护一个日志表，记录哪个会员的哪个商品的哪个订单已经减过了库存，再来同样的消息，就不减了
 *
 *  2）手动ack
 *      1）开启手动ack ，spring.rabbitmq.listener.simple.acknowledge-mode=manual
 *       public void listen（）{
 *           try{
 *               //处理消息，回复成功
 *               channel.basicAck()
 *           }catch(Exception e){
 *               channel.basicNack()/basicReject(true)
 *           }
 *       }
 *
 */

@Service
public class ReceiveUserService {

    /**
     * 以下方法可以接受以下参数
     * 1.org.springframework.amqp.core.Message 既可以获取消息的内容字节，又可以获取消息的其他属性
     * 2.user 明确这个队列是这个类型对象，可以直接写这个类型参数
     * 3. com.rabbitmq.client.Channel
     * @param message
     * 以上参数无任何顺序，也没有数量限制
     */
    @RabbitListener(queues = {"hello"})
    public void receiveUserService(Message message, User user, Channel channel) throws IOException {
        System.out.println("收到的消息是：" + message.getClass());

        //byte[] body = message.getBody();  获取消息内容，字节数组
        //获取消息其他属性
        //MessageProperties messageProperties = message.getMessageProperties();
        System.out.println("收到的消息是：" + user.toString());

        //可以把消息拒绝，让rabbitmq 把消息发送给别人
        /**
         * 如果第二个参数 true 表示重新入队
         * false ，表示不重新入队
         *
         */
       // channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
    }


/*
    @RabbitListener(queues = {"order-queue"})
    public void receiveOrder(Order order,Message  message,Channel channel ) throws Exception {
        System.out.println("监听到订单生成。。。。" + order);
        String orderSn = order.getOrderSn();
        Long skuId = order.getSkuId();
        Integer num = order.getNum();
        System.out.println("系统正在扣除【"+skuId+"】商品的数量，此次扣除【"+num +"】件");

        if(num%2==0){
            System.out.println("系统正在扣除【"+skuId+"】库存失败");
             //channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
            //multiple  如果是true，表示回复g该队列所有的消息，如果是false，表示只回复本条消息
            // requeue ，如果是 true 该条消息表示重回队列 ，
            // false，表示不重回队列，丢掉本条消息 ,会进入到死信队列
            // channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            throw new Exception("库存扣除失败");
        }

        System.out.println("扣除成功");
        //成功了，只回复本条消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
 */


    @RabbitListener(queues = {"user.order.queue"})
    public void receiveDeadQueue(Order order,Message  message,Channel channel ) throws Exception {
        System.out.println("收到过期订单" + order + "正在关闭订单");
        //成功了，只回复本条消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }



}

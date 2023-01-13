package com.huangjiabin._07fanoutExchanges;

import com.huangjiabin.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/*
*fanout扇出交换机 做发布订阅模式，路由为空，核心：绑定交换机的队列因为路由key为空，都能接收到消息
* //声明一个临时队列：
        String queueName=channel.queueDeclare().getQueue(); 后台队列会有两个字母：AD EXCL
*/
public class _01Consumer {
    private final static String EXCHANGE_NAME = "logs";
    public static void main(String[] args) throws Exception {
        Channel channel=RabbitMqUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //声明一个临时队列
        String queueName=channel.queueDeclare().getQueue();
        //绑定其中 routingkey( 也称之为 binding key) 为空字符串
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        System.out.println(" C1等待接收消息, 把接收到的消息打印在屏幕 ........... ");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" 控制台打印接收到的消息"+message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}

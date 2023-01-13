package com.huangjiabin._08directExchange;

import com.huangjiabin.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/*
    direct直接交换机，做路由模式，通过 根据_03Producer发出消息时routingKey去决定消息由谁接收
    _02Consumer和_01Consumer一样只是队列名和routingKey不同

*/
public class _01Consumer {
    private static final String EXCHANGE_NAME = "logs_direct";

    public static void main(String[] args) throws Exception {
        Channel channel=RabbitMqUtils.getChannel();
        channel.queueDeclare("Q1",false,false,true,null);
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        channel.queueBind("Q1",EXCHANGE_NAME,"black");
        DeliverCallback deliverCallback = (consumer,delivery)->{
            String message = new String(delivery.getBody());
            System.out.println("接收到消息"+message);
        };
        System.out.println("队列Q1等待接收消息");
        channel.basicConsume("Q1",true, deliverCallback, CancelCallback->{});

    }
}

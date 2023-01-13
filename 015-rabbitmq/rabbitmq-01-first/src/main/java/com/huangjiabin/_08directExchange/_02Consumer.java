package com.huangjiabin._08directExchange;

import com.huangjiabin.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class _02Consumer {
    private static final String EXCHANGE_NAME = "logs_direct";

    public static void main(String[] args) throws Exception {
        Channel channel= RabbitMqUtils.getChannel();
        channel.queueDeclare("Q2",false,false,true,null);
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        channel.queueBind("Q2",EXCHANGE_NAME,"orange");
        DeliverCallback deliverCallback = (consumer, delivery)->{
            String message = new String(delivery.getBody());
            System.out.println("接收到消息"+message);
        };
        System.out.println("队列Q2等待接收消息");
        channel.basicConsume("Q2",true, deliverCallback, CancelCallback->{});

    }
}

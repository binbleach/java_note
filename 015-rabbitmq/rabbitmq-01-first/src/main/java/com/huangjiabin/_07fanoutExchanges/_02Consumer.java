package com.huangjiabin._07fanoutExchanges;

import com.huangjiabin.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class _02Consumer {
    private final static String EXCHANGE_NAME="logs";

    public static void main(String[] args) throws Exception {
        Channel channel =RabbitMqUtils.getChannel();
        String queueName=channel.queueDeclare().getQueue();
        //channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        System.out.println("C2等待接收消息, 把接收到的消息打印在屏幕 ........... ");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" 控制台打印接收到的消息"+message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}

package com.huangjiabin._03ack;

import com.huangjiabin.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class _01Consumer {
    /*
        核心思想：一个生产者，两个消费者（设置为手动应答），设置睡眠时间，测试当一个消费者宕机，消息是否会被另一个消费者接收
        消息应答分手动应答和自动应答、当应答后队列删除消息，未应答的消息重新入队被其他消费者消费，手动应答有可能会造成消息丢失
        自动应答：
            消费者在下列代码第二个参数设置为true
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
        手动应答：
            A.Channel.basicAck(用于肯定确认) RabbitMQ 已知道该消息并且成功的处理消息，可以将其丢弃了
            B.Channel.basicNack(用于否定确认)
            C.Channel.basicReject(用于否定确认)与 Channel.basicNack 相比少一个批量应答参数 不处理该消息了直接拒绝，可以将其丢弃了
    */
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        Channel channel=RabbitMqUtils.getChannel();
        DeliverCallback deliverCallback = (consumerTag, delivery)->{
            String message = new String(delivery.getBody());
            try {
                Thread.sleep(15*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(message);
            //手动应答
            //第一个参数是消息的标记，第二个参数是是否应答
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),true);
        };
        CancelCallback cancelCallback = (consumerTag)->{
            System.out.println("消费被中断");
        };
        channel.basicConsume(QUEUE_NAME,false,deliverCallback,cancelCallback);
        System.out.println("C2等待接收消息");
    }
}

package com.huangjiabin._02workQueues;

import com.huangjiabin.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/*
这里的思想是：一个生产者两个消费者，消息是轮询分发的，一条消息只被消费了一次,如果有交换机就可以被消费两次
不必写两个消费者代码，在idea配置，找 Allow parallel run(允许并行并行)打勾即可
声明队列channel.queueDeclare(QUEUE_NAME, false, false, false, null);在生产者，所以先启动生产者,
*/
public class _01Consumer {
    private static final String QUEUE_NAME="hello";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String receivedMessage = new String(delivery.getBody());
            System.out.println(" 接收到消息:" + receivedMessage);
        };
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println(consumerTag + " 消费者取消消费接口回调逻辑");
        };
        System.out.println("C2 消费者启动等待消费 .................. ");
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
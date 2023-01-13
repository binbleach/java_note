package com.huangjiabin._10deadqueue;

import com.huangjiabin.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;


public class _02TTLProducer {
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 设置消息的 TTL 时间
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();
        // 该信息是用作演示队列个数限制
        for (int i = 1; i <11 ; i++) {
            String message="info"+i;
            channel.basicPublish("normal_exchange","zhangsan",properties,message.getBytes());
            System.out.println(" 生产者发送消息:"+message);
        }
    }
}

package com.huangjiabin._09topicExchange;

import com.huangjiabin.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/*
    topic主题交换机，主题模式。就是通过匹配符合的routingKey来指定符合的接收队列

    发送到类型是 topic 交换机的消息的 routing_key 不能随意写，必须满足一定的要求，它必须是一个单词列表，以点号分隔开。
    *(星号)可以代替一个单词
    #(井号)可以替代零个或多个单词
    Q1-->绑定的是
    中间带 orange 带 3 个单词的字符串(*.orange.*)
    Q2-->绑定的是
    最后一个单词是 rabbit 的 3 个单词(*.*.rabbit)
    第一个单词是 lazy 的多个单词(lazy.#)
*/
public class _01Consumer {
    private static final String EXCHANGE_NAME = "topic_logs";
    public static void main(String[] argv) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        // 声明 Q1 队列与绑定关系
        String queueName="Q1";
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, EXCHANGE_NAME, "*.orange.*");
        System.out.println(" 等待接收消息 ........... ");
        DeliverCallback deliverCallback = (consumerTag, delivery) ->
        { String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" 接 收 队 列 :"+queueName+"绑定键:"+
                    delivery.getEnvelope().getRoutingKey()+", 消息:"+message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}

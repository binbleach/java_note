package com.huangjiabin.rabbitmq.consumer;

import com.huangjiabin.rabbitmq.config.ConfirmConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class ConfirmConsumer {

    @RabbitListener(queues= ConfirmConfig.QUEUE_NAME)
    public void receiveq1(Message message, Channel channel){
        log.info("当前时间{}，接收到消息{}，来自队列{}",new Date().toString(),new String(message.getBody()),message.getMessageProperties().getConsumerQueue());
    }
}

package com.huangjiabin.stream.service.impl;

import com.huangjiabin.stream.service.IMessageProvider;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;
/*
    内容讲解：
    一、架构：注册中心7001、生产者8001、消费者80、消费者81
    二、消息被重复消费问题
        1、原因：两台消费者没有分组，默认为两组，消息分别被投递
        2、解决：给消费者分组，配置文件中加 group
*/
//定义消息的推送管道
@EnableBinding(Source.class)
public class MessageProviderImpl implements IMessageProvider {

    @Resource
    private MessageChannel output;//消息发送管道

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("*************serial:"+serial);
        return null;
    }
}

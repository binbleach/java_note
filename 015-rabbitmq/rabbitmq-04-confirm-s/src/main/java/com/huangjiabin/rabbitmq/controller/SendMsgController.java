package com.huangjiabin.rabbitmq.controller;

import com.huangjiabin.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/confirm")
public class SendMsgController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*
        发布确认高级：
            1、与基础相比：在ConfirmCallback的基础上加上了ReturnCallback
            2、也可以不走 ReturnCallback，走备份交换机，备份交换机可以路由到备份队（用独立消费者对消息备份）列和报警队列（用独立的消费者来监测报警）
        核心：
            1、正常的交换机队列，生产者消费者
            2、写MyCallBack（回调接口，退回接口）类
            3、写备份交换机：再写一个写正常交换机队列做备份交换机，给正常交换机添加备份交换机参数

        发布确认高级：
        1、ConfirmCallback接口：
            只要消息到达到交换机就会调用此接口方法，用于得知交换机是否接收到消息。
        2、ReturnCallback接口
            只要交换机路由不到队列就会回调此接口方法，用于得知消费者是否接收到消息
        3、备份交换机：
            备份交换机优先级高于ReturnCallback，回退消息先走备份交换机，没有再走ReturnCallback
    */
    @GetMapping("/sendMessage/{message}")
    public void sendMessage(@PathVariable String message){
        //不可路由消息发送，来测试发布确认
        CorrelationData correlationData = new CorrelationData("1001");//设置回调消息的id
        rabbitTemplate.convertAndSend(ConfirmConfig.EXCHANGE_NAME,ConfirmConfig.ROUTINGKEY_NAME+1,message+"key1",correlationData);
        log.info("发的消息内容为：{}",message+"key1");

        //可以路由消息发送
        CorrelationData correlationData1 = new CorrelationData("1002");
        rabbitTemplate.convertAndSend(ConfirmConfig.EXCHANGE_NAME,ConfirmConfig.ROUTINGKEY_NAME,message+"key2",correlationData1);
        log.info("发的消息内容为：{}",message+"key2");
    }

}

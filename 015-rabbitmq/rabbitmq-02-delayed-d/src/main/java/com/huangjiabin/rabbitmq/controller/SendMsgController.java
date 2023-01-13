package com.huangjiabin.rabbitmq.controller;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/*
    RabbitMQ之整合Springboot,死信实现延迟队列：
    一、之前都是在消费者处声明队列交换价整合springboot之后有专门的配置类，在config.TTLQueueConfig
    二、延迟优化：消费者不设置过期时间，可以通过发送者设置延时时间。
    三、延迟队列两种实现：
        1、死信队列。
        2、死信队列做延迟有个缺陷：两条消息就会排队，就算第二条消息延迟时间很短也不会先执行。
           (所以用插件只需一个交换机一个队列即可,不需要死信交换机死信队列。在下一个项目看)
*/
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //给延迟队列发消息
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message){
        log.info("当前时间：{},发送一条信息给两个TTL队列：{}",new Date().toString(),message);
        rabbitTemplate.convertAndSend("X","XA","消息来自ttl为10s的队列"+message);
        rabbitTemplate.convertAndSend("X","XB","消息来自ttl为40s的队列"+message);
    }
    //优化延迟队列，就是Producer处设置延迟时间
    @GetMapping("sendExpirationMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message,@PathVariable String ttlTime) {

        rabbitTemplate.convertAndSend("X", "XC", message, msg ->{
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;});

        log.info(" 当前时间：{}, 发送一条时长{} 毫秒 TTL 信息给队列 C:{}", new Date(),ttlTime, message);
    }


}

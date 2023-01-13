package com.huangjiabin.rabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/*
    基于插件实现延迟队列:不会有排队的bug
    延迟优化：可以通过发送者设置延时时间。
    死信队列做延迟有个缺陷：两条消息就会排队，就算第二条消息延迟时间很短也不会先执行。(所以用插件只需一个交换机一个队列即可)

    发送延迟消息
*/
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("sendDelayedMsg/{message}/{delay}")
    public void sendMsg(@PathVariable String message,@PathVariable Integer delay) {

        rabbitTemplate.convertAndSend("delayed.exchange", "delayed.routingkey", message, msg ->{
            msg.getMessageProperties().setDelay(delay);
            return msg;});

        log.info(" 当 前时间：{}, 基于插件发送一条时长{} 毫秒 TTL 信息给队列 C:{}", new Date(),delay, message);
    }


}

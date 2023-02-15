package com.huangjiabin.site.sys.config;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.huangjiabin.site.sys.mapper.EmailLogMapper;
import com.huangjiabin.site.sys.model.EmailConstants;
import com.huangjiabin.site.sys.model.EmailLog;
import com.huangjiabin.site.sys.service.EmailLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

//@Configuration
@Slf4j
public class RabbitmqConfig {
    @Resource
    private EmailLogMapper emailLogMapper;
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;
    //延迟队列
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    //延迟交换机
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    //routingKey
    public static final String DELAYED_ROUTINGKEY = "delayed.routingkey";
    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        //消息确认回调，确认消息是否到达broker
        //data:消息唯一标识
        //ack:确认结果
        //cause：失败原因
        rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
            System.out.println(data);
            String emailLogId = data.getId();
            if(ack){
                log.info("{}=========>消息发送成功",emailLogId);
                emailLogMapper.update(null,new UpdateWrapper<EmailLog>().set("status",1).eq("email_log_id",emailLogId));
            }else {
                log.info("{}=========>消息发送失败",emailLogId);
            }
        });
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                log.error("{}============>消息发送失败",returnedMessage.getMessage().getBody());
            }
        });
        return rabbitTemplate;
    }

    //生命邮箱队列
    @Bean
    public Queue queue(){
        return new Queue(EmailConstants.EMAIL_QUEUE_NAME);
    }
    //声明邮箱交换机
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(EmailConstants.EMAIL_EXCHANGE_NAME);
    }
    //绑定邮箱队列邮箱交换机
    @Bean
    public Binding  binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(EmailConstants.EMAIL_ROUTING_KEY_NAME);
    }

    //基于插件，声明一个交换机
    @Bean
    public CustomExchange delayedExchange(){
        Map<String,Object> map = new HashMap<>();
        map.put("x-delayed-type","direct");
        /*
            参数 1 交换机名称
            参数 2 交换机类型
            参数 3 是否持久化
            参数 4 是否自动删除
            参数 5 其他参数
        */
        return new CustomExchange(DELAYED_EXCHANGE_NAME,"x-delayed-message",true,false,map);
    }

    //声明一个队列
    @Bean
    public Queue delayedQueue(){
        return new Queue(DELAYED_QUEUE_NAME);
    }

    //绑定
    @Bean
    public Binding bind(@Qualifier("delayedQueue") Queue delayedQueue,
                        @Qualifier("delayedExchange") CustomExchange delayedExchange ){
        return  BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTINGKEY).noargs();
    }
}

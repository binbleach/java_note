package com.huangjiabin.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class DelayedQueueConfig {
    //延迟队列
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    //延迟交换机
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    //routingKey
    public static final String DELAYED_ROUTINGKEY = "delayed.routingkey";

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

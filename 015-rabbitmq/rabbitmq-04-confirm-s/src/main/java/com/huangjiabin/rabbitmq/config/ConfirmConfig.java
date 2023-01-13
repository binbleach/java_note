package com.huangjiabin.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfirmConfig {
    //发布确认配置
    public static final String QUEUE_NAME="confirm.queue";
    public static final String EXCHANGE_NAME = "confirm.exchange";
    public static final String ROUTINGKEY_NAME = "confirm.routingkey";
    //备份交换机配置
    public static final String BACKUP_QUEUE_NAME="backup.queue";
    public static final String WARNING_QUEUE_NAME="warning.queue";
    public static final String BACKUP_EXCHANGE_NAME="backup.exchange";


    //确认交换机。
    @Bean("e1")
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(EXCHANGE_NAME).durable(true)
                //添加属性：备份交换机
                .withArgument("alternate-exchange",BACKUP_EXCHANGE_NAME).build();
    }

    @Bean("q1")
    public Queue confirmQueue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }
    @Bean
    public Binding confirmBinding(@Qualifier("e1") DirectExchange e1,
                                  @Qualifier("q1") Queue q1){
        return BindingBuilder.bind(q1).to(e1).with(ROUTINGKEY_NAME);

    }
    @Bean("backupQueue")
    public Queue backupQueue(){
        return new Queue(BACKUP_QUEUE_NAME);
    }
    @Bean("warningQueue")
    public Queue waringQueue(){
        return new Queue(WARNING_QUEUE_NAME);
    }

    //备份交换机，扇出类型，没有routingKey
    @Bean("backupExchange")
    public FanoutExchange backupExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    @Bean
    public Binding backupQueueBindingbackupExchange(@Qualifier("backupQueue") Queue backupQueue,
                                    @Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    @Bean
    public Binding warningQueueBindingbackupExchange(@Qualifier("warningQueue") Queue warningQueue,
                                                    @Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }

}

package com.huangjiabin.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TTLQueueConfig {
    //普通交换机名称
    public static final String X_EXCHANGE="X";
    //死信交换机名称
    public static final String Y_DEAD_LETTER_EXCHANGE="Y";
    //普通队列名称
    public static final String QUEUE_A="QA";
    public static final String QUEUE_B="QB";
    //延迟优化队列
    public static final String QUEUE_C="QC";
    //死信队列名称
    public static final String DEAD_LETTER_QUEUE_D="QD";


    //声明普通交换机 xExchange是别名
    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }

    //声明死信交换机
    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    //声明普通队列 TTL为 10S
    @Bean("queueA")
    public Queue queueA(){
        Map<String,Object> arguments = new HashMap<>();
        //设置普通队列的属性：死信交换机
        arguments.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        //设置普通队列的属性：routingKey
        arguments.put("x-dead-letter-routing-key","YD");
        //设置普通队列的属性：过期时间TTL,单位毫秒
        arguments.put("x-message-ttl",10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(arguments).build();
    }

    //声明第二个普通队列 TTL为 40S
    @Bean("queueB")
    public Queue queueB(){
        Map<String,Object> arguments = new HashMap<>();
        //设置普通队列的属性：死信交换机
        arguments.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        //设置普通队列的属性：routingKey
        arguments.put("x-dead-letter-routing-key","YD");
        //设置普通队列的属性：过期时间TTL,单位毫秒
        arguments.put("x-message-ttl",40000);
        return QueueBuilder.durable(QUEUE_B).withArguments(arguments).build();
    }

    //声明延迟优化队列，就是普通队列绑定死信交换机，不设置延迟时间，在producer处设置延迟时间
    @Bean("queueC")
    public Queue queueC(){
        Map<String,Object> map = new HashMap();
        map.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key","YD");
        //死信队列无参数
        return QueueBuilder.durable(QUEUE_C).withArguments(map).build();
    }

    //声明死信队列
    @Bean("queueD")
    public Queue queueD(){
        //死信队列无参数
        return QueueBuilder.durable(DEAD_LETTER_QUEUE_D).build();
    }


    //绑定普通交换机绑定队列A
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA,
                        @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");

    }
    //绑定普通交换机绑定队列B
    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");

    }
    //绑定:普通交换机绑定延迟优化队列C
    @Bean
    public Binding queueCBindingX(@Qualifier("queueC") Queue queueD,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueD).to(xExchange).with("XC");

    }
    //绑定:死信交换机绑定死信队列D
    @Bean
    public Binding queueDBindingY(@Qualifier("queueD") Queue queueD,
                                  @Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");

    }




}

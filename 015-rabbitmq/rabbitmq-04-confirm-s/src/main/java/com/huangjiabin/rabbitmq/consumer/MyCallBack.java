package com.huangjiabin.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
/*
    1、继承：RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback
    2、写init、confirm、returnedMessage
    3、添加配置文件，打开发布确认模式和开启消息回退
*/
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    //@PostConstruct该注解被用来修饰一个非静态的void（）方法。被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，
    // 并且只会被服务器执行一次。PostConstruct在构造函数之后执行，init（）方法之前执行。
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    //交换机
    /*  回调方法,只要发布消息就会调用回调函数，得知交换机是否接收到消息。
        参数 1 对调消息的ID及相关信息，在发送时设置
        参数 2 交换机是否收到消息 true收到，false未收到
        参数 3 cause未收到原因，如果收到就是null.
    */
    public void confirm(CorrelationData correlationData, boolean b, String cause) {
        String id=correlationData!=null?correlationData.getId():"";
        if(b){
            log.info("交换机接收到id为：{}的消息",correlationData.getId());
        }else {
            log.info("交换机接未收到id为：{}的消息，原因：{}",correlationData.getId(),cause);
        }
    }

    //路由
    /*
       退回方法 ：当消息在传递过程中到达不了目的地时将消息退回给生产者
    */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("消息：{}，被交换机：{}，退回了，原因是：{}，routingkey是：{}",new String(returnedMessage.getMessage().getBody()),
               returnedMessage.getExchange(),returnedMessage.getReplyText(),returnedMessage.getRoutingKey() );
    }
}

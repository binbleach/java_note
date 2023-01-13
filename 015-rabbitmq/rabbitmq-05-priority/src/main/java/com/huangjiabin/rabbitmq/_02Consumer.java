package com.huangjiabin.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class _02Consumer {
    public static final String QUEUE_NAME="hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.8.128");
        factory.setUsername("root");
        factory.setPassword("root");
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();

        //拉布达斯表达式声明接收消息回调
        DeliverCallback deliverCallback =(consumerTag, message) -> {
            String message1= new String(message.getBody());
            System.out.println(message1);
            System.out.println("-----------------");
        };
        //取消接收消息回调
        CancelCallback cancelCallback = consumerTag->{
            System.out.println("消息被var2中断");
        };
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}

package com.huangjiabin.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/*
    队列的优先级设置：
    1、设置队列最大优先级：ap.put("x-max-priority",10); //优先级区间0~255
    2、设置消息的优先级：
        AMQP.BasicProperties properties=new AMQP.BasicProperties().builder().priority(5).build();
*/
public class _01Producer {
    public static final String  QUEUE_NAME="hello";
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建一个连工厂
        ConnectionFactory factory = new ConnectionFactory();
        //工厂IP连接RabbitMQ的队列
        factory.setHost("192.168.8.128");
        //用户名
        factory.setUsername("root");
        //密码
        factory.setPassword("root");
        //创建连接
        Connection connection=factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();

        Map<String,Object> map = new HashMap<>();
        //01设置队列最大优先级
        map.put("x-max-priority",10); //0~255
        //生产队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,map);
        //发送消息
        System.out.println("aaaaaaaa");
        for(int i=1;i<11;i++){
            String message="Hello World"+i;
            System.out.println("aaaaaaaa");
            if(i==5){
                //02设置消息优先级
                AMQP.BasicProperties properties=new AMQP.BasicProperties().builder().priority(5).build();
                channel.basicPublish("",QUEUE_NAME,properties,message.getBytes());
                System.out.println("发送消息5");
            }else{
                channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
                System.out.println("发送消息"+i);
            }
        }

        System.out.println("消息发送完毕");
    }
}

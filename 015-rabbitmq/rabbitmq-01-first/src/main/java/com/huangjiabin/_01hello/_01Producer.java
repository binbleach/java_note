package com.huangjiabin._01hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/*简单连接不需要交换机*/
public class _01Producer {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws Exception {
        // 创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.8.128");
        factory.setUsername("root");
        factory.setPassword("root");
        //创建连接
        Connection connection = factory.newConnection();
        //获取信道 channel 实现了自动 close 接口 自动关闭 不需要显示关闭
        Channel channel =connection.createChannel();
        /**
        * 生成一个队列
        * 1. 队列名称
        * 2. 队列是否持久化 持久化后台会有大写字母D提示
        * 3. 该队列是否只供一个消费者进行消费 是否进行共享 true 可以多个消费者消费
        * 4. 是否自动删除 最后一个消费者端开连接以后 该队列是否自动删除 true 自动删除
        * 5. 其他参数
        */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "hello world";
        /**
         * 发送一个消息
         * 1. 发送到那个交换机
         * 2. 路由的 key 是哪个，本次是队列名称
         * 3. 其他的参数信息 ，可设置消息持久化MessageProperties.PERSISTENT_TEXT_PLAIN，
              保存到磁盘，也有可能保存到磁盘的过程中宕机。具体持久化看后面的发布确认机制
         * 4. 发送消息的消息体
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" 消息发送完毕");

    }
}

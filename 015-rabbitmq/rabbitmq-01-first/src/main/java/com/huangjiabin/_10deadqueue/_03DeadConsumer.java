package com.huangjiabin._10deadqueue;

import com.huangjiabin.utils.RabbitMqUtils;

//_03只是接收消息的类，死信队列的消息会直接被接收
public class _03DeadConsumer {
    public static void main(String[] args) throws Exception {
        System.out.println("等待接收消息...");
        RabbitMqUtils.getChannel().basicConsume("dead_queue",true,(s, delivery) -> {
            String message = new String(delivery.getBody());
                    System.out.println("死信队列接收到消息"+message);
                },
                s->{});
    }
}

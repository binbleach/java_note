package com.huangjiabin._10deadqueue;

import com.huangjiabin.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
/*
    先启动_01然后停止，再启动_04发送消息到正常队列（设置最大长度的）
*/
public class _04NormalProducer {
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        for(int i=0;i<10;i++){
            String message = "消息"+i;
            channel.basicPublish("normal_exchange","zhangsan",null,message.getBytes());
            System.out.println("正常消息"+message+"发送成功");
        }
    }
}

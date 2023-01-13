package com.huangjiabin._08directExchange;

import com.huangjiabin.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

public class _03Producer {
    private static final String EXCHANGE_NAME = "logs_direct";
    public static void main(String[] argv) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        Scanner sc = new Scanner(System.in);
        System.out.println(" 请输入信息");
        while (sc.hasNext()) {
            String message = sc.nextLine();
            channel.basicPublish(EXCHANGE_NAME, "black", null, message.getBytes("UTF-8"));
            System.out.println(" 生产者发出消息" + message);
        }

    }
}

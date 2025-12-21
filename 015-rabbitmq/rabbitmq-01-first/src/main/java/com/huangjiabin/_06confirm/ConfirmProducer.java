package com.huangjiabin._06confirm;

import com.huangjiabin.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/*
    发布确认，即使设置了队列的持久化和消息的持久化还是不够的，可能消息在持久化过程中rabbitmq宕机导致消息丢失，所以需要发布确认。
    发布确认基础：只用到了ConfirmCallback，确认消息是否发送到Broker服务器，也就是交换机中。
*/
public class ConfirmProducer {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] args) {
        try {
            ConfirmProducer.confirmIndividually();  //962ms
            //ConfirmProducer.confirmBatch();     //105ms
//            confirmBatchAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //单个发布确认
    public static void confirmIndividually() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        Long starTIme=System.currentTimeMillis();
        for(int i=0;i<1000;i++){
            String message = "信息"+i;
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            if(channel.waitForConfirms()){
                System.out.println(message+"发布成功");
            }
        }
        System.out.println("单个消息发布确认时间："+(System.currentTimeMillis()-starTIme)+"ms");
    }

    //批量发布确认
    public static void confirmBatch() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        Long starTIme=System.currentTimeMillis();
        for(int i=0;i<1000;i++){
            String message = "信息"+i;
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            System.out.println(message+"已发送");
            if(i%100==0){
                channel.waitForConfirms();
                System.out.println("消息已经确认");
            }
        }
        System.out.println("消息批量发布确认时间："+(System.currentTimeMillis()-starTIme)+"ms");
    }

    //异步批量确认
    public static void confirmBatchAsync() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        /**
         * 线程安全有序的一个哈希表，适用于高并发的情况
         * 1. 轻松的将序号与消息进行关联
         * 2. 轻松批量删除条目 只要给到序列号
         * 3. 支持并发访问
         */
        ConcurrentSkipListMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();

        //消息发布成功回调
        ConfirmCallback ackCallback = new ConfirmCallback() {
            @Override
            public void handle(long sequenceNumber, boolean multiple) throws IOException {
                if (multiple) {
                    // 返回的是小于等于当前序列号的未确认消息 是一个 map
                    ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(sequenceNumber, true);
                    // 清除该部分未确认消息
                    confirmed.clear();
                    System.out.println("批量确认的消息" + sequenceNumber);
                    System.out.println("multiple:" + multiple);
                } else {
                    // 只清除当前序列号的消息
                    outstandingConfirms.remove(sequenceNumber);
                    System.out.println("确认的消息" + sequenceNumber);
                    System.out.println("multiple:" + multiple);
                }
            }
        };

        //消息发布失败回调
        ConfirmCallback nackCallback = (sequenceNumber, multiple) -> {
            String message = outstandingConfirms.get(sequenceNumber);
            System.out.println(" 发布的消息"+message+" 未被确认，序列号"+sequenceNumber);
        };

        //添加监听器
        channel.addConfirmListener(ackCallback, nackCallback);
        Long starTIme=System.currentTimeMillis();
        for(int i=0;i<1000;i++){
            String message = "信息"+i;
            outstandingConfirms.put(channel.getNextPublishSeqNo(), message);
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            System.out.println(message+"已发送");
        }

        System.out.println("消息批量异步发布确认时间："+(System.currentTimeMillis()-starTIme)+"ms");
    }

}

package com.huangjiabin._10deadqueue;

import com.huangjiabin.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.impl.AMQImpl;

import java.util.HashMap;
import java.util.Map;

/*  核心内容：正常交换机 ——(绑定)> 正常队列 ——(声明时设置参数)> 死信交换机 ——> 死信队列

    死信产生原因:
        消息 TTL 过期  ——> 可以在Producer设置，也可以在Consumer里设置
            测试：开启并停掉_01，再启动_03(设置过期时间的Producer)发送消息，ttl时间过了后，消息会转到死信对列

        队列达到最大长度(队列满了，无法再添加数据到 mq 中)   ——>可以在Consumer里设置
            测试：开启并停掉_01(设置最大长度的队列)，再启动_04,应为连发10条消息，超出的前部分会变成死信消息

        消息被拒绝(basic.reject 或 basic.nack)并且 requeue=false    ——>在Consumer里设置
            测试：开启并停掉_01(设置不自动应答且拒绝一条消息)，再启动_04,被拒绝的消息会变成死信消息

    注：延迟队列时一种特殊的死信队列，就是上面的第二种，可以用来做：
        订单在十分钟之内未支付则自动取消
        用户注册成功后，如果三天内没有登陆则进行短信提醒。
        用户发起退款，如果三天内没有得到处理则通知相关运营人员。 等
*/
public class _01Consumer {
    private final static String EXCHANGE_N = "normal_exchange";
    private final static String EXCHANGE_D = "dead_exchange";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_N,"direct");
        channel.exchangeDeclare(EXCHANGE_D,"direct");
        Map map = new HashMap<>();
        //设置过期时间 可以在producer设置，更加灵活一点 时间单位时ms
        //map.put("x-message-ttl",10000);
        //正常队列设置为死信队列
        map.put("x-dead-letter-exchange",EXCHANGE_D);
        //设置死信routingKey
        map.put("x-dead-letter-routing-key","lisi");
        //设置最大长度为6
        //map.put("x-max-length",6);

        //声明正常队列，其中配置参数绑定死信交换机
        channel.queueDeclare("normal_queue",false,false,false,map);
        channel.queueDeclare("dead_queue",false,false,false,null);
        channel.queueBind("normal_queue",EXCHANGE_N,"zhangsan");
        channel.queueBind("dead_queue",EXCHANGE_D,"lisi");
        System.out.println("等待接收消息...");
        DeliverCallback deliverCallback = (s, delivery) -> {
            String message = new String(delivery.getBody());
            if(message.equals("消息3")){
                System.out.println("正常队列拒绝消息"+message);
                /*拒绝应答：
                    参数 1 消息的tag
                    参数 2 是否重新回到队列requeue
                */
                channel.basicReject(delivery.getEnvelope().getDeliveryTag(),false);
            }else {
                System.out.println("正常队列接收到消息"+message);
                //消息应答： 参数1消息tag 消息2是否批量应答
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
            }

        };
        channel.basicConsume("normal_queue",false,deliverCallback,consumerTag->{});
    }
}

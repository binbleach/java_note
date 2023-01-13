package com.huangjiabin.Jedis;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class keytest {
    public static void main(String[] args) {
        //连接redis
        Jedis jedis = new Jedis("192.168.8.128",6379);//一个ip是linux服务器的ip一个端口号
        //使用Jedis对象操作redis服务

        //查看redis服务是否正常运行：ping 如果正常---返回pong
        String ret=jedis.ping();
        System.out.println(ret);

        //查看所有的key
        Set<String> keys=jedis.keys("*");
        for(String key:keys){
            System.out.println(key);
        }

        //查看key是否存在
        Boolean flag = jedis.exists("k1");
        System.out.println(flag);

        //把某个key移动到指定数据库实例
        long mum=jedis.move("k2",1);
        System.out.println(mum);

        //切换到指定数据库实例
        jedis.select(1);


        String k2 = jedis.get("k2");
        System.out.println(k2);

    }
}

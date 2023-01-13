package com.huangjiabin.Jedis;

import redis.clients.jedis.Jedis;

public class jedisString {
    public static void main(String[] args) {
        //连接redis服务
        Jedis jedis = new Jedis("192.168.8.128",6379);

        //使用jedis对象对redis 字符串类型的数据进行操作
        String ret=jedis.set("k3","v3");  //OK
        System.out.println(ret);

        Long num=jedis.append("k3","我是最佳页"); //17,中文占3个
        System.out.println(num);

        String k3=jedis.get("k3"); //v3我是最佳页
        System.out.println(k3);
    }
}

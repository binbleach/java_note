package com.huangjiabin.Jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class jedisTransaction {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.8.128",6379);

        //开启事务
        Transaction tran=jedis.multi();  //后面用tran调，可以压到队列
        tran.set("k6","v6");
        tran.set("k7","v7");
        //结束事务
        tran.exec();
    }
}

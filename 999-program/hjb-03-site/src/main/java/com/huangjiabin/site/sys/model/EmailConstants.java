package com.huangjiabin.site.sys.model;

public class EmailConstants {
    //消息投递中
    public static final Integer DELIVERING=0;
    //消息投递成功
    public static final Integer SUCCESS = 1;
    //消息投递失败
    public static final Integer FAILURE = 2;
    //最大尝试次数
    public static final Integer MAX_TRY_COUNT = 3;
    //消息超时时间
    public static final Integer MSG_TIMEOUT = 1;
    public static final String EMAIL_QUEUE_NAME = "email.queue";
    public static final String EMAIL_EXCHANGE_NAME = "email.exchange";
    public static final String EMAIL_ROUTING_KEY_NAME = "email.routing.key";

}

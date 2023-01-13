package com.huangjiabin.webservice.properties;

public class PoolCxfClientProperties {
    /**连接超时*/
    private int connectionTimeout = 10000;
    /**接收超时*/
    private int receiveTimeout = 60000;

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getReceiveTimeout() {
        return receiveTimeout;
    }

    public void setReceiveTimeout(int receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }
}

/*
 * Copyright 2019 the original author or authors.
 */
package com.huangjiabin.webservice.properties;

/**
 * cxf连接池的配置参数
 */
public class CxfClientPoolProperties {
    /**
     * 连接池最大值
     */
    private int maxTotal = 3000;

    /**
     * 每个key的最大
     */
    private int maxTotalPerKey = 20;

    /**
     * 设置为true时，池中无可用连接，borrow时进行阻塞；为false时，当池中无可用连接，抛出NoSuchElementException异常
     */
    private boolean blockWhenExhausted = true;

    /**
     * 每个key对应的连接池最小空闲连接数
     */
    private int minIdlePerKey = 0;

    /**
     * 最大等待时间，当需要borrow一个连接时，最大的等待时间，如果超出时间，抛出NoSuchElementException异常，-1为不限制时间
     */
    private long maxWaitMillis = -1;

    /**
     * 获取连接时检测连接的有效性
     */
    private boolean testOnBorrow = true;

    /**
     * 返还连接时检测连接的有效性
     */
    private boolean testOnReturn = false;

    /**
     * 空闲时进行连接测试，会启动异步evict线程进行失效检测
     */
    private boolean testWhileIdle = false;

    /**
     * 失效检测时间，需要testWhileIdle为true，默认30秒
     */
    private int timeBetWeeNevicTionRuns = 30;

    /**
     * 连接的空闲的最长时间，需要testWhileIdle为true，默认24分钟
     */
    private int minEvictAbleIdleTime = 24;

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxTotalPerKey() {
        return maxTotalPerKey;
    }

    public void setMaxTotalPerKey(int maxTotalPerKey) {
        this.maxTotalPerKey = maxTotalPerKey;
    }

    public boolean isBlockWhenExhausted() {
        return blockWhenExhausted;
    }

    public void setBlockWhenExhausted(boolean blockWhenExhausted) {
        this.blockWhenExhausted = blockWhenExhausted;
    }

    public int getMinIdlePerKey() {
        return minIdlePerKey;
    }

    public void setMinIdlePerKey(int minIdlePerKey) {
        this.minIdlePerKey = minIdlePerKey;
    }

    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public int getTimeBetWeeNevicTionRuns() {
        return timeBetWeeNevicTionRuns;
    }

    public void setTimeBetWeeNevicTionRuns(int timeBetWeeNevicTionRuns) {
        this.timeBetWeeNevicTionRuns = timeBetWeeNevicTionRuns;
    }

    public int getMinEvictAbleIdleTime() {
        return minEvictAbleIdleTime;
    }

    public void setMinEvictAbleIdleTime(int minEvictAbleIdleTime) {
        this.minEvictAbleIdleTime = minEvictAbleIdleTime;
    }
}

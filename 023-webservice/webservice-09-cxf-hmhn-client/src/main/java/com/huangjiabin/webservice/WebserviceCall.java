package com.huangjiabin.webservice;

import com.huangjiabin.webservice.impl.DefaultCxfClient;
import com.huangjiabin.webservice.properties.CxfClientPoolProperties;
import com.huangjiabin.webservice.properties.PoolCxfClientProperties;

import java.util.HashMap;
import java.util.Map;

public class WebserviceCall {
    public CxfClient cxfClient;

    public WebserviceCall(){
        PoolCxfClientProperties poolCxfClientProperties = new PoolCxfClientProperties();
        //连接超时时间（单位毫秒）
        poolCxfClientProperties.setConnectionTimeout(1000);
        //接收超时时间（单位毫秒）
        poolCxfClientProperties.setReceiveTimeout(6000);

        CxfClientPoolProperties cxfClientPoolProperties = new CxfClientPoolProperties();
        //连接池最大值
        cxfClientPoolProperties.setMaxTotal(3000);
        //每个Key最大值，超过8有问题
        cxfClientPoolProperties.setMaxTotalPerKey(8);
        //设置为true时，池中无可用连接，borrow时进行阻塞；为false时，当池中无可用连接，抛出NoSuchElementException异常
        cxfClientPoolProperties.setBlockWhenExhausted(true);
        // 每个key对应的连接池最小空闲连接数
        cxfClientPoolProperties.setMinIdlePerKey(0);
        //最大等待时间，当需要borrow一个连接时，最大的等待时间，如果超出时间，抛出NoSuchElementException异常，-1为不限制时间
        cxfClientPoolProperties.setMaxWaitMillis(10000);
        //获取连接时检测连接的有效性
        cxfClientPoolProperties.setTestOnBorrow(true);
        //返还连接时检测连接的有效性
        cxfClientPoolProperties.setTestOnReturn(false);
        //空闲时进行连接测试，会启动异步evict线程进行失效检
        cxfClientPoolProperties.setTestWhileIdle(false);
        //失效检测时间，需要testWhileIdle为true，默认30秒
        cxfClientPoolProperties.setTimeBetWeeNevicTionRuns(30);
        //连接的空闲的最长时间，需要testWhileIdle为true，默认24分钟
        cxfClientPoolProperties.setMinEvictAbleIdleTime(24);
        this.cxfClient = new DefaultCxfClient(poolCxfClientProperties, cxfClientPoolProperties);
    }
    public String invoke(String methodName, Object... param){
        return "";
    }
}

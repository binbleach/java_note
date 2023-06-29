package com.huangjiabin.webservice.factory;

import com.huangjiabin.webservice.properties.CxfClientPoolProperties;
import com.huangjiabin.webservice.properties.PoolCxfClientProperties;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.cxf.endpoint.Client;

public class
CxfClientPoolFactory {
    public static GenericKeyedObjectPool<String, Client> createPool(){
        return createPool(new PoolCxfClientProperties(),new CxfClientPoolProperties());
    }
    public static GenericKeyedObjectPool<String, Client> createPool
            (PoolCxfClientProperties poolCxfClientProperties, CxfClientPoolProperties cxfClientPoolProperties){
        PoolCxfClientFactory cxfClientFactory = new PoolCxfClientFactory(poolCxfClientProperties);
        GenericKeyedObjectPoolConfig<Object> config = new GenericKeyedObjectPoolConfig<>();
        //连接池最大值
        config.setMaxTotal(cxfClientPoolProperties.getMaxTotal());
        //每个key的最大
        config.setMaxTotalPerKey(cxfClientPoolProperties.getMaxTotalPerKey());
        //设置为true时，池中无可用连接，borrow时进行阻塞；为false时，当池中无可用连接，抛出NoSuchElementException异常
        config.setBlockWhenExhausted(cxfClientPoolProperties.isBlockWhenExhausted());
        //每个key对应的连接池最小空闲连接数
        config.setMinIdlePerKey(cxfClientPoolProperties.getMinIdlePerKey());
        //最大等待时间，当需要borrow一个连接时，最大的等待时间，如果超出时间，抛出NoSuchElementException异常，-1为不限制时间
        config.setMaxWaitMillis(cxfClientPoolProperties.getMaxWaitMillis());
        config.setNumTestsPerEvictionRun(Integer.MAX_VALUE);
        //获取连接时检测连接的有效性
        config.setTestOnBorrow(cxfClientPoolProperties.isTestOnBorrow());
        //返还连接时检测连接的有效性
        config.setTestOnReturn(cxfClientPoolProperties.isTestOnReturn());
        //空闲时进行连接测试，会启动异步evict线程进行失效检测
        config.setTestWhileIdle(cxfClientPoolProperties.isTestWhileIdle());
        //-1不启动。30min一次(默认1mis)  失效检查线程运行时间间隔，如果小于等于0，不会启动检查线程
        config.setTimeBetweenEvictionRunsMillis(cxfClientPoolProperties.getTimeBetWeeNevicTionRuns() * 60000L);
        //连接的空闲的最长时间，需要testWhileIdle为true，默认24分钟
        config.setMinEvictableIdleTimeMillis(cxfClientPoolProperties.getMinEvictAbleIdleTime() * 60 * 60000L);

        return new GenericKeyedObjectPool(cxfClientFactory);
    }
}

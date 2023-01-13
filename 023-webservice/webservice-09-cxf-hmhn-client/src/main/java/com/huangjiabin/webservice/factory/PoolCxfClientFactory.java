package com.huangjiabin.webservice.factory;

import com.huangjiabin.webservice.properties.PoolCxfClientProperties;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import java.io.File;
import java.util.List;

/**
 * @author puwei
 * @email puwei@yinhai.com
 * @date 2020/7/27
 * @time 9:56
 * @since 1.0
 */
public class PoolCxfClientFactory extends BaseKeyedPooledObjectFactory<String, Client> {
    /**连接配置*/
    PoolCxfClientProperties poolCxfClientProperties;

    public PoolCxfClientFactory(){
        this.poolCxfClientProperties = new PoolCxfClientProperties();
    }

    public PoolCxfClientFactory(PoolCxfClientProperties poolCxfClientProperties){
        this.poolCxfClientProperties = poolCxfClientProperties;
    }

    @Override
    public Client create(String key) throws Exception {
        System.out.println("来了大姐");
        Bus bus = BusFactory.getThreadDefaultBus();
        JaxWsDynamicClientFactory dcf = new JaxWsDynamicClientFactory(bus) {
            @Override
            protected boolean compileJavaSrc(String classPath, List<File> srcList, String dest) {
                org.apache.cxf.common.util.Compiler compiler = new org.apache.cxf.common.util.Compiler();
                compiler.setClassPath(classPath);
                compiler.setOutputDir(dest);
                compiler.setEncoding("utf-8");
                compiler.setTarget("1.8");
                return compiler.compileFiles(srcList);
            }
        };

        Client client = dcf.createClient(key);
        /*HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setAllowChunking(false);
        //获取连接超时时间
        policy.setConnectionTimeout(this.cxfClientProperties.getConnectionTimeout());
        //获取接收超时时间
        policy.setReceiveTimeout(this.cxfClientProperties.getReceiveTimeout());
        http.setClient(policy);*/
        return client;
    }

    @Override
    public PooledObject<Client> wrap(Client client) {
        return new DefaultPooledObject(client);
    }

}


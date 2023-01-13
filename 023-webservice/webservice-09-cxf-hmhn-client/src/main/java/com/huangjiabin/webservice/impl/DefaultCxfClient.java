package com.huangjiabin.webservice.impl;

import com.huangjiabin.webservice.CxfClient;
import com.huangjiabin.webservice.factory.CxfClientPoolFactory;
import com.huangjiabin.webservice.factory.PoolCxfClientFactory;
import com.huangjiabin.webservice.properties.CxfClientPoolProperties;
import com.huangjiabin.webservice.properties.PoolCxfClientProperties;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.cxf.endpoint.Client;

import java.util.Map;

public class DefaultCxfClient implements CxfClient {
    private GenericKeyedObjectPool<String, Client> clientPool;
    public DefaultCxfClient(){
        this.clientPool = CxfClientPoolFactory.createPool();
    }
    public DefaultCxfClient(PoolCxfClientProperties cxfClientProperties,CxfClientPoolProperties cxfClientPoolProperties){
        this.clientPool = CxfClientPoolFactory.createPool(cxfClientProperties,cxfClientPoolProperties);
    }
    @Override
    public String invoke(String wsdlUrl, String methodName, String param, String nameSpace, Map soapMap) throws Exception {
        System.out.println("来了老弟？");
        //从池中获取连接对象
        Client client = clientPool.borrowObject(wsdlUrl);
        client.getOutInterceptors().clear();
        if (nameSpace != null && soapMap != null) {
//            client.getOutInterceptors().add(new AddSoapHeader(nameSpace, soapMap));
        }
        Object[] obj;
        try {
            obj = client.invoke(methodName, param);
        } finally {
            clientPool.returnObject(wsdlUrl, client);
        }
        Object result = obj[0];
        return (String) result;
    }
}

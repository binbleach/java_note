package com.huangjiabin.webservice;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.cxf.endpoint.Client;

import java.util.Map;

public interface CxfClient {
    String invoke(String wsdlUrl, String methodName,String param, String nameSpace, Map soapMap) throws Exception;
}

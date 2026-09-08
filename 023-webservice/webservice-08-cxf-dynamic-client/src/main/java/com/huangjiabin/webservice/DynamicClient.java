package com.huangjiabin.webservice;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*
    cxf的动态调用
    JaxWsDynamicClientFactory 不是线程安全的，全局单例在多线程下并发创建客户端导致内部状态冲突
*/
public class DynamicClient {

    private static final JaxWsDynamicClientFactory CLIENT_FACTORY = JaxWsDynamicClientFactory.newInstance();

    public static final ThreadPoolExecutor THREAD_POOL =
            new ThreadPoolExecutor(12, 24, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100),
                    Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws Exception {

        // 提交任务
        List<Future<?>> futureList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Future<?> future = THREAD_POOL.submit(() -> {
                    Client client = null;
                    try {
                        // 报错
//                        client = CLIENT_FACTORY.createClient("http://127.0.0.1:12345/weather?wsdl");
                        // 不报错
                        client = JaxWsDynamicClientFactory.newInstance().createClient("http://127.0.0.1:12345/weather?wsdl");
                        Object[] result2 = client.invoke("queryWeather", "北凉");
                        System.out.println(result2[0]);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("当前程序存在未捕获异常！！！");
                    }
            });
            futureList.add(future);
        }
        for (Future<?> future : futureList) {
            future.get();
        }
        System.out.println("结束===========================");
    }
}

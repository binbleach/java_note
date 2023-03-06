package com.huangjiabin.gateway.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/*
  内容讲解：
    一、搭建gateway网关的普通路由：
        1、新建网关服务 gateway-9527，注册进eureka-7001(单机)中，并路由到provider-8001的接口中，uri: http://localhost:8001
        2、分别启动 7001、8001、9527测试：
            访问：http://localhost:9527/payment/get/1 会路由到 http://localhost:8001/payment/get/1 即成功
            访问：http://localhost:9527/game 会路由到 http://news.baidu.com/game 即成功
    二、搭建gateway网关的动态路由：
        3、将yml配置中的普通路由uri 换成动态的，路由到微服务中，uri: lb://springcloud-eureka-provider
        4、分别启动 7001、8001、8002、9527测试：访问：http://localhost:9527/payment/get/1 会路由到
            http://localhost:8001/payment/get/1和http://localhost:8002/payment/get/1 即成功
    三、搭建和配置自定义gateway网关 filter
        5、自定义GlobalFilter，过滤请求中参数username为空的
        6、分别启动 7001、8001、8002、9527测试：访问：http://localhost:9527/payment/get/1 报错
            访问 http://localhost:9527/payment/get/1?username=11 路由成功，即成功
    四、配置gateway网关路由（普通/动态）
        1、第一种：在yml中配置：spring.cloud.gateway.routes
        2）第二种：配置 bean：RouteLocator（动态路由没讲）
    五、配置gateway网关filter
        1）filter类实现 GlobalFilter 和 Ordered ，重写其接口
    六、配置gateway网关路由的断言（yml中）：
        1）- Path=/payment/get/**  #路径匹配
        2）- After=2023-03-03T16:07:03.685+08:00[Asia/Shanghai]    # 时间后匹配
        3）- Before=2023-03-03T16:11:03.685+08:00[Asia/Shanghai]   # 时间前匹配
        4）- Between=2023-03-03T16:07:03.685+08:00[Asia/Shanghai],2023-03-03T16:11:03.685+08:00[Asia/Shanghai] # 时间区间内匹配
        5）- Cookie=username,zzyy  #cookie匹配，逗号前面为key，逗号后面为值匹配的正则
             cmd中测：curl http://localhost:9527/payment/get/1     404
             cmd中测：curl http://localhost:9527/payment/get/1 --cookie "username=zzyy"   请求成功
        6）- Header=X-Request-Id,\d+  #请求头访问，逗号前面为key,逗号后面为值匹配的正则
            curl http://localhost:9527/payment/get/1 -H "X-Request-Id:aaa"  404
            curl http://localhost:9527/payment/get/1 -H "X-Request-Id:123"  请求成功
        7）- Host=huang.jia.bin   # 不晓得啥子匹配
            curl http://localhost:9527/payment/get/1  404
            curl http://localhost:9527/payment/get/1 -H "host:huang.jia.bin"  请求成功
        8）- Method=GET    #请求类型匹配
            curl -X -POST http://localhost:9527/payment/get/1  404
            curl http://localhost:9527/payment/get/1  请求成功
        9）- Query=username,\d+  # 请求参数断言要有参数名username并且值还要是正整数才能路由
             curl http://localhost:9527/payment/get/1?username=-1   404
             curl http://localhost:9527/payment/get/1?username=1    请求成功
      五、curl命令：
        1、curl是利用URL语法在命令行方式下工作的开源文件传输工具。它被广泛应用在Unix、多种Linux发行版中、
           并且有DOS和Win32、Win64下的移植版本。postman这种底层就是掉的这个。
        2、curl使用：
            1）curl https://www.baidu.com/           # GET请求, 输出 响应内容
            2）curl -X POST https://www.baidu.com/   # POST请求, 输出 响应内容
*/

@Configuration
public class GatewayConfig {

    // RouteLocator 可配置多个，同样生效
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        //测试了几遍id随便写不唯一也不影响
        RouteLocator aaa = routeLocatorBuilder.routes()
                .route("aaa", predicateSpec -> predicateSpec.path("/ent")
                                                                .uri("http://news.baidu.com/ent"))
                .route("bbb", predicateSpec -> predicateSpec.path("/game")
                                                                .uri("http://news.baidu.com/game"))
                .build();
        return aaa;
    }
}

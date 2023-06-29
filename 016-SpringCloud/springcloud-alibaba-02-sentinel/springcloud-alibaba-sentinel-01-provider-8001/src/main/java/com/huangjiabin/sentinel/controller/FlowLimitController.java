package com.huangjiabin.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.ResultCodeEnum;
import com.huangjiabin.sentinel.myHandler.CustomerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/*
    内容讲解：
        一、用sentinel监测provider8001
            1、启动sentinel：执行 java -jar sentinel-dashboard.jar
            2、启动nacos：bin目录下执行 startup.cmd -m standalone
            3、创建provider8001，配置集成sentinel-dashboard和nacos-server即可
            4、Sentinel采用的懒加载，要先访问provider8001才开始监控
        二、sentinel讲解：
            1、簇点链路：有我们的服务列表，我们可以对服务进行流控、降级、热点、授权。
            2、流控规则：
                1）资源名：必须为服务路径名称否则无法流控
                2）针对来源：sentinel可以针对调用者进行限流，填写微服务名称即可。默认default（不区分来源）
                3）阈值类型：
                    • QPS（每秒钟的请求数量）：当调用该服务的QPS达到阈值的时候，进行限流
                        QPS = 并发线程数 * (1000 / 平均耗时ms)
                    • 线程数：当调用该服务的线程数达到阈值的时候，进行限流
                        注意：用两个浏览器来测试...
                4）是否集群：这里没配置
                5）流控模式
                    • 直接: api达到限流条件时，直接限流（自作自受）
                    • 关联:当关联的资源达到阈值时，就限流自己（父债子偿）
                    • 链路:只记录指定链路上的流量(指定资源从入口资源进来的流量，如果达到阈值，就进行限流))[api级别的针对来源]
                6）流控效果:
                    • 快速失败:直接失败，抛异常
                        ◦ 效果：Blocked by Sentinel (flow limiting)
                        ◦ 源码：com.alibaba.csp.sentinel.slots.block.flow.controller.DefaultController
                    • Warm Up（预热）: 根据codeFactor (冷加载因子，默认3)的值，从‘设置阈值/codeFactor’，经过预热时长，才达到设置的QPS闻值
                        ◦ 使用场景：秒杀系统开启瞬间，会有很多流量上来，很有可能把系统打死。通过预热保护系统。
                        ◦ 源码：com.alibaba.csp.sentinel.slots.block.flow.controller.WarmUpController
                        ◦ 效果：未达到预热时长时流控阈值为‘设置阈值/codeFactor’，到达预热时长后流控阈值为设置阈值。注：闻值类型必须设置为QPS
                    • 排队等待:匀速排队，让请求以匀速的速度通过，闻值类型必须设置为QPS，否则无效
            3、降级规则：
                1）熔断策略
                    • 慢调用比例 (SLOW_REQUEST_RATIO)：请求时间大于 ’RT‘ 的请求统计为慢调用。统计时长（statIntervalMs默认1000ms）内
                        请求数目大于设置的’最小请求数目‘，且慢调用的比例大于设置的’比例阈值‘，则熔断。
                        ’熔断时长‘结束进入半开，下一个请求非慢调用则停止熔断，否则继续熔断。
                    • 异常比例 (ERROR_RATIO)：和慢调用类似，区别是统计慢调用比例换成异常比例。所以不用设置 ’RT‘
                    • 异常数 (ERROR_COUNT)：和异常比例类似，区别是统计异常数量，而不是比例。所以比例阈值变为异常数
                    官网：https://github.com/alibaba/Sentinel/wiki/%E7%86%94%E6%96%AD%E9%99%8D%E7%BA%A7
            4、@SentinelResource：
                1）BlockException：是sentinel各个规则类型异常的父类，当我们配置的规则超出阈值时sentinel会抛出该异常，其中包含：
                                    FlowException：限流异常；ParamFlowException：热点参数限流的异常；DegradeException：降级异常
	                                AuthorityException：授权规则异常；SystemBlockException：系统规则异常；
                1）value：对应资源名。sentinel可根据资源名或url匹配规则。请保证value的唯一性，且不要与url相同，会出错。
                          注：url匹配的规则，BlockException异常默认处理：Blocked by Sentinel (flow limiting)
                2）blockHandler：自定义BlockException处理方法。方法必须接收 BlockException参数，不接收则无效。
                                注：不配置或无效的blockHandler，异常时：Whitelabel Error Page。
                3）fallback：自定义程序异常时回调。不配置异常时：Whitelabel Error Page。
            5、热点规则：
                1）代码中要配合@SentinelResource 使用，配置服务降级。
                2）参数索引：要限制参数的索引（从0开始）
                3）单机阈值：因为热点规则的限流模式默认且固定为QPS，所以单机阈值是每秒钟超过该请求数量则限流
                4）统计窗口时长：统计阈值的窗口期
                5）参数例外项：可以设置参数在特定值下的阈值
            6、系统规则：
                1）是全局的限流规则
            7、sentinel整合feign：
                1）sentinel激活对feign支持，1.8版本后不需要：feign.sentinel.enabled=true
                2）如consumer-80
            8、sentinel持久化（拿流控规则举例，其他规则请看https://blog.csdn.net/qq_36763419/article/details/121560105）：
                1）配置yml：
                    datasource:
                        flow:
                          nacos:
                            server-addr: localhost:8848
                            dataId: ${spring.application.name}-flow-rules
                            groupId: SENTINEL_GROUP
                            data-type: json     #配置格式
                            rule-type: flow     #规则类型，这里是流控规则
                2）在nacos中新增一个配置文件：
                • data id：${spring.application.name}-flow-rules
                • group：SENTINEL_GROUP
                • 配置格式：json
                • 内容：
                    [
                        {
                            "resource": "testB",        //资源名
                            "limitApp": "default",      //来源应用
                            "grade": 1,                 //阈值类型。0 表示线程数，1 表示是QPS
                            "count": 1,                 //单机阈值
                            "strategy": 0,              //流控模式。0 表示直接，1 表示关联，2 表示链路。
                            "controlBehavior": 0,       //流控效果。0 表示快速失败，1 表示Warm up，2 表示排队等待。
                            "clusterMode": false        //是否集群。false 表示否，true 表示是。
                        }
                    ]
                3）直接启动微服务，访问testB对应的资源即可。可以看见在流控规则处多了一个生效的规则

*/
@RestController
public class FlowLimitController {

    //测试流控和降级
    @GetMapping("/testA")
    public String testA()
    {
        //暂停2s，测试sentinel阈值类型为线程数的流控效果
//        try { TimeUnit.MILLISECONDS.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        return "------testA";
    }

    //全局降级方法
    @SentinelResource(value = "testB",blockHandlerClass = CustomerBlockHandler.class,blockHandler = "handlerException")
    @GetMapping("/testB")
    public CommonResult testB()
    {
        return new CommonResult(ResultCodeEnum.SUCCESS,"testB成功....");
    }


    //测试热点规则
    @SentinelResource(value = "testToKey",blockHandler = "deal_testToKey")
    @GetMapping("/testToKey")
    public String testToKey(String p1,String p2){
        return "testToKey....";
    }

    public String deal_testToKey(String p1,String p2, BlockException blockException){
        return "deal_testToKey...";
    }

}



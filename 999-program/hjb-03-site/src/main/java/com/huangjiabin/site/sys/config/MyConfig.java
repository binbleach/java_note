package com.huangjiabin.site.sys.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/*
*   自定义线程池，会覆盖名称是asyncExecutor ，用得时候@Async(value="asyncExecutor")，不指定应该也可以
 */
@EnableAsync
@Configuration
public class MyConfig {
//    private static final Logger logger = LoggerFactory.getLogger(MyConfig.class);
//    @Bean
//    public ThreadPoolTaskExecutor asyncExecutor() {
//        logger.info("start asyncExecutor......");
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        //配置核心线程数
//        executor.setCorePoolSize(16);
//        //配置最大线程数
//        executor.setMaxPoolSize(64);
//        //配置队列大小
//        executor.setQueueCapacity(9999);
//        //配置线程池中的线程的名称前缀 (指定一下线程名的前缀)
//        executor.setThreadNamePrefix("async-huang-");
//        // rejection-policy：当pool已经达到max pool size的时候，如何处理新任务
//        // CALLER_RUNS：不在新线程中执行任务，而是由调用线程（提交任务的线程）处理该任务
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        //执行初始化
//        executor.initialize();
//        return executor;
//    }

}

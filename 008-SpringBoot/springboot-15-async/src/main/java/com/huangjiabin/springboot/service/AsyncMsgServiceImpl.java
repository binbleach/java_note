package com.huangjiabin.springboot.service;

import com.huangjiabin.springboot.execute.AsyncResult;
import com.huangjiabin.springboot.model.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class AsyncMsgServiceImpl implements AsyncMsgService {
    @Autowired
    private Msg msg;
    private Logger logger = LoggerFactory.getLogger(AsyncMsgServiceImpl.class);
    //不用注解，直接用注入对象来异步处理
    @Autowired
    private ThreadPoolTaskExecutor applicationTaskExecutor;

    @Async
    @Override
    public Future<Msg> asyncSendMsg() {
        logger.info("asyncSendMsg start...");
        System.out.println("是的消息已经发送");
        System.out.println("当前线程："+Thread.currentThread().getName());
        msg.setTitle("101");
        msg.setContent("我是一条消息");
        logger.info("asyncSendMsg end!!!");
        return new AsyncResult<Msg>(msg);
    }

    @Async("asyncExecutor") //带不带名称都可以
    @Override
    public void asyncSendMsg2() {
        logger.info("asyncSendMsg start...");
        System.out.println("当前线程："+Thread.currentThread().getName());
        logger.info("asyncSendMsg end!!!");
    }

    @Override
    public Object asyncSendMsg3() {
        logger.info("start executeAsync......");

        List<Object> resultList = new ArrayList<>();

        //使用Future方式执行多任务，生成一个结果集合，
        List<Future> futures = new ArrayList<>();
        //查询出需要发送短信的用户手机号
        for (int i = 0; i < 1000; i++) {
            //并发处理
            Future<String> future = applicationTaskExecutor.submit(() -> {
                //给用户发送营销短信
                System.out.println("恭喜您获得15天VIP体验资格.");
                String result = "恭喜您获得15天VIP体验资格.";
                return result;
            });
            futures.add(future);
        }

        try {
            //查询任务执行的结果
            for (Future<?> future : futures) {
                while (true) {//CPU轮询：每个future都并发轮循，判断完成状态然后获取结果
                    //future.isDone() && !future.isCancelled() 表示异步任务结束获取到结果。
                    if (future.isDone() && !future.isCancelled()) {//获取future成功完成状态，或者使用future.get(1000*1, TimeUnit.MILLISECONDS)
                        Object i = future.get();//获取结果
                        System.out.println("任务 i=" + i + " 获取完成!" + new Date());
                        resultList.add(i);
                        break;//当前future获取结果完毕，退出while循环
                    } else {
                        Thread.sleep(5);//每次轮询休息5毫秒（CPU纳秒级），避免CPU高速轮循耗空CPU
                    }
                }
                //Object i = future.get();
                //Object i2 = future.get(1, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("end executeAsync......");

        return resultList;
    }
}

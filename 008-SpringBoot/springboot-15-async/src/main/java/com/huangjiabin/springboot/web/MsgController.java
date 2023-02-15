package com.huangjiabin.springboot.web;

import com.huangjiabin.springboot.model.Msg;
import com.huangjiabin.springboot.service.AsyncMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/*
    内容讲解：
    1、关于线程：springboot会默认给我们一个线程池，供我们调用
            context-> beanFactory-> singletonObjects-> applicationTaskExecutor(线程池)
    2、springboot要开启异步任务：1）在声明bean的地方加上@EnableAsync。 2）在开启异步类或方法上加上@Async
    3、要想接收到异步方法执行的结果，并在方法执行完毕前调用，需要实现Future接口，自定义构造方法
    4、自定以线程池：声明一个 ThreadPoolTaskExecutor 类型的bean xxx.开启异步任务@Async(value="xxx")。带不带bean名称都可以，建议还是带着
    5、不用@Async自己编写异步任务
*/
@Controller
public class MsgController {
    @Autowired
    private AsyncMsgService asyncMsgService;
    private Logger logger= LoggerFactory.getLogger(MsgController.class);

    /*
        测试异步线程
    */
    @RequestMapping("/msg/sendMsg")
    public @ResponseBody Msg sendMsg() throws InterruptedException {
        logger.info("Async start...");
        //异步发送消息，不阻塞。
        Future<Msg> msg=asyncMsgService.asyncSendMsg();
        logger.info("Async end!!!");
        try {
            return msg.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    //测试自定义线程池
    @RequestMapping("/msg/sendMsg2")
    public @ResponseBody void sendMsg2() {
        logger.info("Async start2...");
        asyncMsgService.asyncSendMsg2();
        logger.info("Async end2!!!");
    }

    //测试不用 @Async注解，手动异步编程
    @RequestMapping("/msg/sendMsg3")
    public @ResponseBody Object sendMsg3() throws InterruptedException, ExecutionException {
        logger.info("Async start3...");
        //调用service层的异步任务
        //(发短信，要发10几万短信，为了提高发短信的效率，所以可以把发短信用线程池执行，同时可以有多个线程在执行发短信)
        Object result =  asyncMsgService.asyncSendMsg3();
        logger.info("Async end3!!!");
        return result;

    }
}

package com.huangjiabin.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
    logback使用：
    1、resources目录下生成logback-spring.xml文件，配置日志输出文件路径和指定包。
       相关配置不用logback-spring.xml配置文件也可以，在application.yml中配置即可
    2、springboot集成logback不需要引入依赖。因为boot-start里面引入了。
    3、使用logback：
      1）添加依赖lombok，加@Slf4j注解。
      2）或者 直接 Logger logger =LoggerFactory.getLogger(clazz) -> logger.ingo("...");
    4、日志介绍：
        1）common-logging：是早期的日志门面。本身包含了一个Simple Logger，但是功能很弱。在运行的时候它会先在CLASSPATH找log4j，
            如果有，就使用log4j，如果没有，就找JDK1.4带的 java.util.logging，如果也找不到就用Simple Logger
        2）slf4j 也是日志门面，提供了接口，由日志框架实现。
        3）log4j： apache 出 的日志框架。
        4）logback：log4j的创始人做的日志框架。相比log4j它有更好的特性。
        5）log4j2：apache对log4j的升级。
    5、springboot的logging配置：在yml种看吧


*/
@Controller
@Slf4j
public class LogbackController {
    private Logger logger = LoggerFactory.getLogger(LogbackController.class);

    @RequestMapping(value = "/student/count")
    public @ResponseBody String getStudentCount(){

        log.trace("trace==================");
        log.debug("debug==================");
        log.info("info=====================");
        log.warn("warn=================");
        log.error("error=================");
        logger.info("info2=====================");
        return "ok";
    }

}

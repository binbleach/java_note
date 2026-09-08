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
    1、springboot集成spring-boot-starter-logging，默认日志实现是 logback，不需要单独引入。
    2、application.yml可以代替logback-spring.xml(推荐)/logback.xml文件，进行基础的配置
    3、使用logback：
      1）添加依赖lombok，加@Slf4j注解。调用：log.info("参数：{}", data);
      2）原生 SLF4J写法： private static final Logger log = LoggerFactory.getLogger(Demo.class);
    4、log4j的使用类似，不过springboot的话要先排除默认依赖再引入，配置文件 log4j2.xml/log4j2-spring.xml
    5、日志介绍：
        1）门面：只提供 API（JCL 、SLF4J、jboss-logging）
          实现：真正输出日志（logback、log4j1、log4j2、JUL java.util.logging）
        1）common-logging（简称JCL,J代表Jakarta）：是早期的日志门面，采用的是“运行时动态查找"机制。
            运行时会遍历classpath下配置，先找 log4j → 再找 JUL (java.util.logging) → 兜底 SimpleLog
            缺陷：复杂的类加载器容易出现日志失效，动态查找的性能开销。 Spring Boot 2.x等主流框架基本淘汰了它。
            老项目可以在 Maven 中排除 commons-logging，引入 jcl-over-slf4j 桥接包，实现 SLF4J+logback
        2）slf4j 也是日志门面，提供了接口，由日志框架实现。
        3）log4j： apache 出 的日志框架。
        4）logback：log4j的创始人做的日志框架。相比log4j它有更好的特性。
        5）log4j2：apache对log4j的升级。
    6、springboot的logging配置：在yml中看吧

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

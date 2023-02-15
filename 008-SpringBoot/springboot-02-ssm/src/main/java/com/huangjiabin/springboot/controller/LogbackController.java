package com.huangjiabin.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
    logback使用：
    1、resources目录下生成logback-spring.xml文件配置日志输出文件路径和指定包
    2、添加依赖lombok

*/
@Controller
@Slf4j
public class LogbackController {

    @RequestMapping(value = "/student/count")
    public @ResponseBody String getStudentCount(){

        log.trace("trace==================");
        log.debug("debug==================");
        log.info("info=====================");
        log.warn("warn=================");
        log.error("error=================");
        return "ok";
    }

}

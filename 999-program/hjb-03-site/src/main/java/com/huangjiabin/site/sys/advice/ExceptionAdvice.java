package com.huangjiabin.site.sys.advice;


import com.huangjiabin.site.sys.model.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * web控制层异常统一处理
 */
@ControllerAdvice//辅助Controller
@Slf4j
public class ExceptionAdvice {

    //捕获所有异常
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RespBean defaultErrorHandler(Exception e) {
        //得到异常栈的首个元素
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        //异常类全路径
        log.error("path: " + stackTraceElement.getClassName());
        //异常行数
        log.error("line: " + stackTraceElement.getLineNumber());
        //异常信息
        log.error("error: " + e.toString());

        return RespBean.error(e.getMessage());
    }
}


package com.bin.springai.config;


import com.bin.springai.function.TimeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

/*
    函数式工具调用，配置类
*/
@Configuration
public class FunctionCallConfiguration {

    @Bean
    @Description("根据用户输入的时区获取该时区的当前时间")
    public Function<TimeService.Request, TimeService.Response> getTimeFunction(TimeService timeService){
        return timeService::getTimeByZoneId;
    }
}

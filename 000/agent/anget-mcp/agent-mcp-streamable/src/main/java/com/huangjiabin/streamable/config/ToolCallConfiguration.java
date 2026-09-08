package com.huangjiabin.streamable.config;

import com.huangjiabin.streamable.tools.WeatherTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolCallConfiguration {

    @Bean
    public ToolCallbackProvider weatherTools(WeatherTool weatherTool) {
        // 自动扫描 WeatherService 中带有 @Tool 注解的方法
        return MethodToolCallbackProvider.builder().toolObjects(weatherTool).build();
    }

}

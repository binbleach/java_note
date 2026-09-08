package com.huangjiabin.streamable.config;

import com.huangjiabin.streamable.tools.GoodsTool;
import com.huangjiabin.streamable.tools.OrderTool;
import com.huangjiabin.streamable.tools.TradeTool;
import com.huangjiabin.streamable.tools.WeatherTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoodsToolCallConfiguration {

    @Bean
    public ToolCallbackProvider goodsTools(GoodsTool goodsTool, OrderTool orderTool, TradeTool tradeTool) {
        // 自动扫描 WeatherService 中带有 @Tool 注解的方法
        return MethodToolCallbackProvider.builder()
                .toolObjects(goodsTool, orderTool, tradeTool).build();
    }

}

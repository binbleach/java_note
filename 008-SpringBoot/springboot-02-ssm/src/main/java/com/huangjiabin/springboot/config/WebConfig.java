package com.huangjiabin.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc   //表示全面接管SpringMVC，SpringBoot对SpringMVC的自动配置不需要了，所有的都是我们自己配，所有默认配置都没了
@Component
public class WebConfig implements WebMvcConfigurer {

    // jsp 视图前缀
    @Value("${spring.mvc.view.prefix}")
    private String prefix;

    // jsp 视图后缀
    @Value("${spring.mvc.view.suffix}")
    private String suffix;

    // jsp 视图的使用顺序
    @Value("${spring.mvc.view.order}")
    private int order;

    // 重新配置静态资源目录
    @Value("${spring.web.resources.static-locations}")
    private String[] staticLocations;

    // 重新配置静态资源模型路径
    @Value("${spring.mvc.static-path-pattern}")
    private String staticPathPattern;

    // 使用jsp 视图的请求路径
    @Value("${spring.mvc.view.viewName}")
    private String[] viewName;

    /**
     * 注册jsp视图解析器
     * @description: 以viewResolver命名。阻止ContentNegotiatingViewResolver注入I0C容器，可当做jsp视图解析器
     * 为什么要阻止ContentNegotiatingViewResolver注入呢，因为这个视图解析器的默认order特别小，总放在集合最前面，它会选择最优视图解折器
     * @return
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix(prefix);    //优先级大于配置文件
        resolver.setSuffix(suffix);
        //设置可以被这个视图解析器处理的视图名称或视图名称模式
        resolver.setViewNames(viewName);
        resolver.setOrder(order);//设置优先级,数值越小优先级越高
        return resolver;
    }

    /**
     * 配置静态文件映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置静态资源处理
        registry.addResourceHandler(staticPathPattern)
                .addResourceLocations(staticLocations);
    }
}

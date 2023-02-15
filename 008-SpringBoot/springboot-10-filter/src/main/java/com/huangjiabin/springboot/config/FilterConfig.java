package com.huangjiabin.springboot.config;

import com.huangjiabin.springboot.filter.MyFilter2;
import com.huangjiabin.springboot.filter.MyFilter3;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new MyFilter2());
        filterRegistrationBean.addUrlPatterns("/user/*"); //注这里只能定义当前包下的，/user/** 是错的，不能拦截到下级包
        //设置过滤器顺序
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }
    @Bean
    public FilterRegistrationBean filterRegistrationBean2() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new MyFilter3());
        filterRegistrationBean.addUrlPatterns("/user/*"); //注这里只能定义当前包下的，/user/** 是错的，不能拦截到下级包
        //设置过滤器顺序
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
    //字符编码过滤器
    @Bean FilterRegistrationBean characterEncodingFilterRegistrationBean(){
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);//强制使用
        characterEncodingFilter.setEncoding("utf-8");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(characterEncodingFilter);
        filterRegistrationBean.addUrlPatterns("/*");//设置拦截的请求
        return filterRegistrationBean;
    }
}

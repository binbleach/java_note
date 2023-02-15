package com.huangjibin.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*
    打war包
    要打包就要继承SpringBootServletInitializer，重写configure方法
    还有pom下<packaging>war</packaging> ,resources，jsp编译配置。然后mvn package
    1）其中 SpringBootServletInitializer implements WebApplicationInitializer
        tomcat启动时会回调 WebApplicationInitializer.onStartup()代替了web.xml
    脱离idea 部署war ，复制war包到tomcat安装路径下的webapps目录下，然后在bin目录下直接双击startup.bat就好了。
    注：之前在application.properties设置的上下文根和端口号就失效了，以部署到的tomcat为准
*/
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
}

package com.huangjiabin.site.sys.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Value("${swagger.enable}")
    private Boolean enable;
    @Bean
    public Docket createRestApi(){
        //链式编程（构建器模式），基本是固定；
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable)//可以开启或关闭swagger文档
                .apiInfo(apiInfo())
                .select()
                //.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.basePackage("com.huangjiabin.site.sys"))
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))//扫描有ApiOperation注解的方法
                .paths(PathSelectors.any())//任何路劲
                //.paths((s) -> !s.contains("/login")) //不包含login登录的路径
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());

    }
    /**
     * 创建api的基本信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        //链式编程（构建器模式），基本是固定；
        return new ApiInfoBuilder()
                .title("校园公共活动场所管系统接口文档")
                .description("集成Swagger2构建RESTful APIs")
                .termsOfServiceUrl("http://www.huangjiabin.com/")
                .contact(new Contact("huangjiabin","http:localhost:8080/doc.html","2901038144@qq.com"))
                //.license("采用 Apache 2.0 开源许可证")
                //.licenseUrl("http://http://www.bjpowernode.com/licence.txt")
                .version("1.0.0")
                .build();
    }
    private List<ApiKey> securitySchemes(){
        List<ApiKey> result = new ArrayList<>();
        //参数要求三个
        ApiKey apiKey = new ApiKey("Authorization","Authorization","Header");
        result.add(apiKey);
        return result;
    }
    private List<SecurityContext> securityContexts(){
        List<SecurityContext> result = new ArrayList<>();
        result.add(getContextByPath("/test/.*"));//表示test下的都要
        return result;
    }
    private SecurityContext getContextByPath(String path){
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(path))
                .build();
    }
    private List<SecurityReference> defaultAuth(){
        List<SecurityReference> result = new ArrayList();
        AuthorizationScope authorizationScope = new
                AuthorizationScope("global","accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0]=authorizationScope;
        result.add(new SecurityReference("Authorization",authorizationScopes));
        return result;
    }
}

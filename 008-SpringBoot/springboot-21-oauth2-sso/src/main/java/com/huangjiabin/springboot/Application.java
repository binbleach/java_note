package com.huangjiabin.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
//开启单点登录功能
@EnableOAuth2Sso
public class Application {
	//先启动授权服务器 36 oauth2-jwt那个
	//访问http://localhost:8081/user/getCurrentUser会跳到服务端授权的页面http://localhost:8080/login
	//输入admin，123456完成验证后，返回还是http://localhost:8081/user/getCurrentUser页面
	//实现单点登录
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

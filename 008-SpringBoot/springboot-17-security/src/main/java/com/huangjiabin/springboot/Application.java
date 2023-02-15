package com.huangjiabin.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/*
	1、只是通过导入SpringSecurity依赖什么都不设置，访问/login.html时会弹出SpringSecurity自己的登录验证
	  用户名时user,密码是打在控制台的字符串
    2、自定义登录逻辑：
    	1)通过创建配置类SecurityConfig 创建Bean对象 返回的是PasswordEncoder的实现类也就是new BCryptPasswordEncoder()，用来对密码操作比如加密判断是否匹配等
    	2)创建UserDetailsService实现类，实现loadUserByUsername
	3、更改登录界面（改成自己的login.html）:
		1）让SpringSecurity配置类继承WebSecurityConfigurerAdapter类
		2）实现configure(HttpSecurity http)方法，加代码：http.formLogin().loginPage("/login.html");
		3）加http.authorizeHttpRequests().anyRequest().authenticated();表示任何请求都需要认证，有点类似拦截器
		4）加http.authorizeHttpRequests().antMatchers("/login.html").permitAll();表示login.html不需要认证
		5）加http.formLogin().loginProcessingUrl("/login");添加登录验证页面，不需要实现，会触发去执行UserDetailsServiceImpl
		6）关闭csrf保护csrf：http.csrf().disable();
		7）设置登录成功后跳转页面：http.formLogin().successForwardUrl("/toMain");登录成功后页面必须是post请求
		8）设置登录失败后跳转页面，也必须是post请求，然后也对失败显示页面放行
		9）可以设置请求的username,password别名
		10）可以设置请求成功后页面跳转不走post,走post就不能直接跳到html页面
		11）失败的也一样
		12）自定义403页面
		0）access:在配置类SecurityConfig配置的授权认真底层都是返回实现了access
		1）自定义access
		2）@SpringBootApplication 开启security注解
		3）@Secured("ROLE_abc") 判断角色注解
		4）@PreAuthorize方法执行前判断权限注解/@PostAuthorize方法执行后判断权限注解
		5）thymeleaf整合security,


*/
@SpringBootApplication
//开启security注解 prePostEnabled包含@PreAuthorize和@PostAuthorize
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

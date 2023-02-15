package com.huangjiabin.springboot.config;

import com.huangjiabin.springboot.handler.MyAccessDeniedHandler;
import com.huangjiabin.springboot.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //引入自定义异常类
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    //引入登录逻辑类
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    //引入dataSource
    @Autowired
    private DataSource dataSource;
    //引入persistentTokenRepository持久层对象，用于记住我功能
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;
    @Bean
    public PasswordEncoder getPw(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/static/login.html")
//                .loginPage("/showLogin")
                //当发现请求是/login时认位是登录，(提交表单的参数也必须是username,password,请求必须是post)，去执行UserDetailsServiceImpl
                //.usernameParameter("name")可以设置请求参数不是username；password也是一样的
                .loginProcessingUrl("/login")
                //用这个登录成功后页面必须是post请求，就不能直接写html。
                .successForwardUrl("/toMain")
                //.successHandler(new MyAuthenticationSuccessHandler("/main.html")) //如果前后端分离项目,上面的换成这个，然后实现AuthenticationSuccessHandler接口
                //登录失败也必须是post请求
                .failureForwardUrl("/toError");
                //.failureHandler(new MyAuthenticationFailureHandler("/error.html"));

        //授权认证
        //authorizeHttpRequests可以以后深究，两者区别
        http.authorizeRequests()
                /*
                     antMatchers匹配，放行
                     ?：匹配一个字符
                     *：匹配0个或多个字符
                     **：匹配0个或多个目录
                */
                //普通html页面不放行也不能从controller跳转，但是thymeleaf页面可以，所以放行了showLogin不用放行thymeleaf里的login.html也行
                //.antMatchers("/login.html").permitAll()
                //.antMatchers("/error.html").permitAll()
                .antMatchers("/showLogin","/static/**","/js/**","/css/**","/**/*.jpg").permitAll()
                //和上面的一样，只不过是用access实现的
                //.antMatchers("/**/login.html").access("permitAll()")
                //和antMatchers一样，也是放行，
                //.regexMatchers(".+[.]png").permitAll()
                //前面多了一个请求类型的参数如果请求类型不一致还是会拦截，不放行
                //.regexMatchers(HttpMethod.POST,"demo").permitAll()
                //如果后台配了spring.mvc.servlet.path=/xxx 。想要demo放行则可以这样配
                //.mvcMatchers("/demo").servletPath("xxx").permitAll()  //servletPath是mvcMatchers独有
                //如果后台配了spring.mvc.servlet.path=/xxx 想要放行demo又不想用mvcMatchers也可以这样配
                //.antMatchers("/yyy/xxx/demo").permitAll()
                //匹配单个权限才能通过访问，区分大小写的
                //.antMatchers("/main1.html").hasAuthority("admin")
                //匹配多个权限才能通过访问
                //.antMatchers("/main1.html").hasAnyAuthority("admin","normal")
                //匹配单个角色才能通过访问，区分大小写，不写ROLE_
                //.antMatchers("/main.html").hasRole("abc")
                //和上面的一样，用的access实现，里面得用单引号
                //.antMatchers("/main.html").access("hasRole('abc')");
                //匹配多个角色才能通过访问
                //.antMatchers("/main1.html").hasAnyRole("abc","abcd")
                //匹配ip才能通过访问
                //.antMatchers("/main1.html").hasIpAddress("127.0.0.1");
                //拦截所有请求只有登录后才能访问
                .anyRequest().authenticated();
                //所有请求必须满足我自定义得返回true才能访问，
                //.anyRequest().access("@myServiceImpl.hasPermission(request,authentication)");
                /*
                    权限之间任意一个达成则放行，只要页面被拦截则都需要登录anyRequest()则所有页面都需要登录
                    permitAll：允许所有人访问
                    denyAll:   不允许任何人访问
                    anonymous： 可以匿名访问和permitAll类似
                    authenticated： 需要认证才能访问
                    fullyAuthenticated：记住我进来的不能访问，得登录
                    rememberMe：只有记住我的用户才能访问
                    hasIpAddress：只有ip地址相同才能访问
                    hasRole：只有配有相同得角色才能访问
                */

        //关闭csrf保护
        //http.csrf().disable();

        //异常处理
        http.exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);

        //记住我
        http.rememberMe()
                //失效时间，默认两周，单位秒
                .tokenValiditySeconds(60)
                //改登录界面设置的记住复选框的name属性，我这里没改
                .rememberMeParameter("remember-me")
                //自定义登录逻辑
                .userDetailsService(userDetailsService)
                //持久层对象
                .tokenRepository(persistentTokenRepository);
        //退出登录跳转页面
        http.logout()
                //设置退出请求路径默认就/logout
                //.logoutUrl("/user/logout")
                //销毁http session对象
                //.invalidateHttpSession()
                //清除认证状态
                //.clearAuthentication()
                //自定义退出成功处理器
                //.logoutSuccessHandler()
                .logoutSuccessUrl("/login.html");

    }
    @Bean
    public PersistentTokenRepository getPersistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //自动建表，建完记得去掉，否则再次启动会报错
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

}

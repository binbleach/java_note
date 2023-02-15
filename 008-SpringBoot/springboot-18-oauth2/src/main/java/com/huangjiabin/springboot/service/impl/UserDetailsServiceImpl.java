package com.huangjiabin.springboot.service.impl;

import com.huangjiabin.springboot.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder pw;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("进入了loadUserByUsername");
        String password=pw.encode("123456");
        //这个user是SpringSecurity里的User，我们可以自己定义
        //return new User(username,password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_abc,/main.html"));
        //自定义的User，这里实现了UserDetails接口
        return new User("admin",password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        //http://localhost:8080/oauth/authorize?response_type=code&client_id=admin&redirect_uri=http://www.baidu.com&scope=all
        /*
        授权码验证：
         一、获取授权码
            1、浏览器访问：http://localhost:8080/oauth/authorize?response_type=code&client_id=admin&redirect_uri=http://www.baidu.com&scope=all
            2、得到：https://www.baidu.com/?code=vJ59M8
         二、获取token
            1、postman访问:http://localhost:8080/oauth/token
                1）Authorization页：
                    1）Type=BasicAuth
                    2）username=admin,password=112233
                2）Body页：选x-www-form-urlencoded
                   参数：
                        grant_type,value=authorization_code
                        code,value=vJ59M8  #这里value是获取到的授权码
                        client_id=admin
                        redirect_uri=http://www.baidu.com
                        scope=all
                3）得到：
                   令牌："access_token": "9d9a1c06-a661-487f-8052-da66534fc0ce",
                   类型："token_type": "bearer",
                   时间："expires_in": 43199,
                   范围："scope": "all"
         三、用token获取资源
            1、postman访问：http://localhost:8080/user/getCurrenUser
            2、Authorization页：
                1）Type->BearerToken
                2）Token=9d9a1c06-a661-487f-8052-da66534fc0ce
            3）post提交拿到字眼服务器的资源，拿到的是controller里配置的authentication.getPrincipal()

        */
        /*
            密码模式：
                1、验证服务器type改成password
                2、重写configure方法，其中需要类要到Security配置类里配
            验证密码模式：
                1、postman，发起获取token请求:http://localhost:8080/oauth/token
                    1）Authorization页设置不变
                    2）Body页参数：
                    grant_type=password
                    username=admin
                    password=123456
                    scope=all
                2、利用1获取的token向资源服务器发起请求:http://localhost:8080/user/getCurrentUser
                    1）Authorization页:token=1中获取token
                3、得到：
                    {
                    "username": "admin",
                    "password": "$2a$10$E2f0QxXRGT7zVIASJLnKQ.qDtzETnPOSsZW3D1r9YmhOgV8qxrcQu",
                    "authorities": [
                        {
                            "authority": "admin"
                        }
                    ],
                    "enabled": true,
                    "accountNonLocked": true,
                    "credentialsNonExpired": true,
                    "accountNonExpired": true
                    }   注：是controller中设置：authentication.getPrincipal()

        */

    }
}

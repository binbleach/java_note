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
        String password = pw.encode("123456");
        //这个user是SpringSecurity里的User，我们可以自己定义
        //return new User(username,password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_abc,/main.html"));
        //自定义的User，这里实现了UserDetails接口
        return new User("admin", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        /*
            整合jwt用的密码模式，生成的token变成jwt：
            {
                "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
                                .eyJleHAiOjE2NDQxMzU4MjAsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiYWRtaW4iXSwianRpIjoiYzhmNjlkYzItZGE2NC00NTcwLThhMTItMjdkOTUwY2QzYmRmIiwiY2xpZW50X2lkIjoiYWRtaW4iLCJzY29wZSI6WyJhbGwiXX0
                                .r3NF4ZM-HIZAY0v3zSUodbKozLdoaBU7OsMQocLV1ho",
                "token_type": "bearer",
                "expires_in": 43199,
                "scope": "all",
                "jti": "c8f69dc2-da64-4570-8a12-27d950cd3bdf"
            }
            可以拿token去jwt官网解析
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

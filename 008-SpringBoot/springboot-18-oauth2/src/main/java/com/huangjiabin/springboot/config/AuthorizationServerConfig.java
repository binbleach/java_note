package com.huangjiabin.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;

//授权服务器配置

@Configuration
//表示启用授权服务器配置
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    //密码模式需要
    @Autowired
    private AuthenticationManager authenticationManager;
    //密码模式需要
    @Resource
    private UserDetailsService userDetailsService;
    //把token存到redis需要
    @Autowired
    @Qualifier("redisTokenStore")
    private TokenStore tokenStore;
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //正常情况下是授权服务器注册的时候生成的
        clients.inMemory()
                //配置client-id
                .withClient("admin")
                //配置client-secret
                .secret(passwordEncoder.encode("112233"))
                //配置访问token的有效时间，时间是秒
                //.accessTokenValiditySeconds(3600)
                //配置redirect_uri,用于授权成功后跳转
                .redirectUris("http://www.baidu.com")
                //配置申请的权限范围
                .scopes("all")
                //配置grant_type,表示授权类型，这里是授权码模式
                //.authorizedGrantTypes("authorization_code");
                //密码模式
                .authorizedGrantTypes("password");
    }

    //密码模式需要
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService)
                    //把token存到redis，不用我们再操作了
                    .tokenStore(tokenStore);
    }

}

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
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    //映入jwt
    @Qualifier("jwtTokenStore")
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //正常情况下是授权服务器注册的时候生成的
        clients.inMemory()
                //配置client-id
                .withClient("admin")
                //配置client-secret
                .secret(passwordEncoder.encode("112233"))
                //配置访问token的有效时间，时间是秒
                .accessTokenValiditySeconds(3600)
                //配置refresh_token有效期
                .refreshTokenValiditySeconds(863000)
                //配置redirect_uri,用于授权成功后跳转
                //.redirectUris("http://www.baidu.com")
                //这里配的是跳转到客户端做单点登录的
                .redirectUris("http://localhost:8081/login")
                //自动授权配置
                .autoApprove(true)
                //配置申请的权限范围
                .scopes("all")
                //配置grant_type,表示授权类型，这里是授权码模式
                //.authorizedGrantTypes("authorization_code");
                //密码模式+刷新令牌（获取token会额外获得"refresh_token"），还可以加授权模式
                .authorizedGrantTypes("password", "refresh_token","authorization_code");
        //后面跟具获得的刷新令牌，在body配grant_type=refresh_token,refresh_token=刷新令牌。然后得到新的token和刷新令牌

    }

    //密码模式需要
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //配置Jwt内容增强器
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(delegates);

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                //配置存储令牌策略
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(enhancerChain);
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //获取密钥需要身份证，使用单点登录时必须配置
        security.tokenKeyAccess("isAuthenticated()");
    }

}

package com.huangjiabin.springboot.config;

import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

//jwt内容增强器
//新增的内容可以吧token解析就可以看到了
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("myIdea", "很想你");
        infoMap.put("reality", "无能为力");
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(infoMap);
        return oAuth2AccessToken;
    }
}

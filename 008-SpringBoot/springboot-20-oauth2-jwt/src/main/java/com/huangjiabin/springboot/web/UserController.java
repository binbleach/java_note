package com.huangjiabin.springboot.web;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;


@RestController
@RequestMapping("/user")
public class UserController {

    //获取当前用户并解析Jwt
    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(HttpServletRequest request, Authentication authentication) {
        String head = request.getHeader("Authorization");
        //bearer+一个空格，所有是加7
        String token = head.substring(head.indexOf("bearer") + 7);
        return Jwts.parser().setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token).getBody();
        /*
            这里想演示，请求token正常请求，然后获取资源的话
            Authorization->no Auth
            header:
                Authorization=bearer空格+token
            得到：
            {
                "user_name": "admin",
                "scope": [
                    "all"
                ],
                "reality": "无能为力",
                "exp": 1644199797,
                "authorities": [
                    "admin"
                ],
                "jti": "0b35248e-79eb-4502-93e4-5e667df7a804",
                "client_id": "admin",
                "myIdea": "很想你"
            }
        */
    }
}

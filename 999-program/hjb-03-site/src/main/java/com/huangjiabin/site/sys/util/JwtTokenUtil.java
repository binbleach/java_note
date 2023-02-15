package com.huangjiabin.site.sys.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    private static final String CLAIN_KEY_USERNAME="sub";
    private static final String CLAIN_KEY_CREATED="created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /*
        根据用户名和当前时间获取token
    */
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put(CLAIN_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIN_KEY_CREATED,new Date());
        return generateToken(claims);
    }
    /*
         根据用户名和当前时间获取token
    */
    private String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpiration())
                    .signWith(SignatureAlgorithm.HS512,secret)
                    .compact();
    }
    private Date generateExpiration(){
        return new Date(System.currentTimeMillis()+expiration);
    }
    /*
        根据token获取用户名
    */
    public String getUserNameByToken(String token){
        return getClaimsFromToken(token).getSubject();
    }
    /*
        获取token荷载
    */
    private Claims getClaimsFromToken(String token){
        return Jwts.parser()//设置的盐
                .setSigningKey("place")
                //请求携带的token
                .parseClaimsJws(token)
                .getBody();
    }
    /*
         验证token是否生效
    */
    public boolean verifyToken(UserDetails userDetails,String token){
        String username = userDetails.getUsername();
        return username.equals(getUserNameByToken(token)) && !isTokenExpiration(token);
    }
    /*
        验证token是否过期
    */
    public boolean isTokenExpiration(String token){
        return getClaimsFromToken(token).getExpiration().before(new Date());
    }
    /*
        验证token是否能被刷新
    */
    public boolean canRefreshToken(String token){
        return !isTokenExpiration(token);
    }
    /*
        刷新token，就是改过期时间
    */
    public String refreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIN_KEY_CREATED,new Date());
        return generateToken(claims);
    }
    /*根据邮箱验证码获取token*/
    public String generateEmailToken(String code){
        Map<String,Object> claims = new HashMap<>();
        claims.put("code",code);
        claims.put(CLAIN_KEY_CREATED,new Date());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+1000*3600))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }
    /*根据token获取验证码*/
    public String getCodeByToken(String token){
        return (String) getClaimsFromToken(token).get("code");
    }
}

package com.huangjiabin.springboot;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class ApplicationTests {

	@Test
	void jwtCreateToken() {
		//过期时间
		Long exp=System.currentTimeMillis()+60*5000;
		JwtBuilder jwtBuilder = Jwts.builder()
									//声明的标识{“jti":"8888”}
									.setId("888")
									//主体，用户{“sub”:"Rose"}
									.setSubject("Rose")
									//签发时间
									.setIssuedAt(new Date())
									//过期时间
									.setExpiration(new Date(exp))
									//自定义声明
									.claim("myIdea","我想你了")
									//可以自定义多个
									.claim("reality","我无能为力")
									//自定义还可以传map
									//.addClaims(map)
									//编码和盐，盐是保存在服务器的，签发和认证都靠他，别让人知道
									.signWith(SignatureAlgorithm.HS256,"xxxx");
		//获取jwt的token
		String token = jwtBuilder.compact();
		System.out.println(token);
		System.out.println("===============================");
		//是三位点分割的，\\是转义符
		String[] split = token.split("\\.");
		System.out.println("头部："+Base64Codec.BASE64.decodeToString(split[0]));
		System.out.println("荷载"+Base64Codec.BASE64.decodeToString(split[1]));
		//签名是无法解密的，因为有盐
		System.out.println("签名"+Base64Codec.BASE64.decodeToString(split[2]));

	}
	@Test
	public void testParseToken(){
		String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjQ0MDkwODE2LCJleHAiOjE2NDQwOTA5MzYsIm15SWRlYSI6IuaIkeaDs-S9oOS6hiIsInJlYWxpdHkiOiLmiJHml6Dog73kuLrlipsifQ.Pp1g4WG1YkaBTJk_heIrwLHSpRTUxhgYYBtxdmWFmsc";
		//解析token，获取负载中声明的对象
		Claims claims=Jwts.parser()
							//设置的盐
							.setSigningKey("xxxx")
							//请求携带的token
							.parseClaimsJws(token)
							.getBody();
		System.out.println("标识id=============="+claims.getId());
		System.out.println("主题subject========="+claims.getSubject());
		System.out.println("签发时间issuedAt===="+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
		System.out.println("现在时间nowTime====="+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		System.out.println("过期时间expiration=="+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
		System.out.println("我的想法"+claims.get("myIdea"));
		System.out.println("现实是"+claims.get("reality"));
	}

}

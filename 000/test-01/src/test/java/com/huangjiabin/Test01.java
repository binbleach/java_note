package com.huangjiabin;


import com.auth0.jwt.algorithms.Algorithm;
import org.junit.Test;
import com.auth0.jwt.JWT;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Test01 {

    /**
        jwt
    */
    @Test
    public void test01() {
        String token = "";
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256("ThisIsTheBestWorld");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        Map map = new HashMap<>();
        map.put("idCard","510902199507236534");
        map.put("username","曾小明");
//        String jsonString = JSON.toJSONString(map);
        String jsonString = "1ROLo7FT1qK6t3RJldu+uuDZ2wM3JWNJvm/gdtgBhNEeNSRvN0QjmAyVeujWf/JHiEbJ+dVFBMcf4DOxRWmtAw==";
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(60*30);
        Date currentDate = new Date();
        // 创建一个Calendar实例，并设置其时间为当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        // 为Calendar实例添加30分钟
        calendar.add(Calendar.MINUTE, 30);
        token = JWT.create()
                .withIssuer("ZSQL")
                .withAudience(jsonString)
                .withIssuedAt(currentDate)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
        System.out.println(token);
    }
}

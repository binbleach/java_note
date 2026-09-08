package com.huangjiabin.a02_javahttp;


import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JavaHttp {
    public static void main(String[] args) {
//        https://cn.bing.com/search?q=%E4%BD%A0%E5%A5%BD&form=ANNTH1&refig=689aac08bf9d421a9f16b9133b3c960e&pc=LCTS
        String s;
        try {
            Map paramMap = new HashMap();
            paramMap.put("q", URLEncoder.encode("你好", "utf-8"));
            paramMap.put("form","ANNTH1");
            paramMap.put("refig","689aac08bf9d421a9f16b9133b3c960e");
            paramMap.put("pc","LCTS");
            s = get("https://cn.bing.com/search", paramMap);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    *   原生 HttpURLConnection (JDK 内置)
    */
    public static String get(String url, Map<String, String> params) throws IOException {
        StringBuilder query = new StringBuilder();
        params.forEach((k, v) -> query.append(k).append("=").append(v).append("&"));
        String fullUrl = url + "?" + query.substring(0, query.length() - 1);

        HttpURLConnection conn = (HttpURLConnection) new URL(fullUrl).openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000); // 5秒连接超时

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            return in.lines().collect(Collectors.joining());
        } finally {
            conn.disconnect();
        }
    }

    /*
    *   Apache HttpClient 5.x (推荐生产使用)
    */
    public static String get2(String url) {
        // 创建 HttpClient 实例
        String result = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            result =  httpClient.execute(request, response -> {
                int status = response.getCode();
                return EntityUtils.toString(response.getEntity());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

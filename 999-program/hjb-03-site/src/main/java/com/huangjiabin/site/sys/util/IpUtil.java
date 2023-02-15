package com.huangjiabin.site.sys.util;


import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class IpUtil {
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }
    public static String getIpInfo(String ip) {
        ip="223.104.216.2";
        // 百度地图申请的ak
        String ak = "WsaaVqdPdcGZDfphd1A9v3LCxSGojiGT";
        // 这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
        JSONObject json = null;
        String address="";
        try {
            json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ak="+ak+"&ip="+ip+"&coor=bd09ll");
            //这里只取出了两个参数，根据自己需求去获取
            JSONObject obj = (JSONObject) ((JSONObject) json.get("content")).get("address_detail");
            String province = obj.getString("province");
            System.out.println(province);

            JSONObject obj2 = (JSONObject) json.get("content");
            address = obj2.getString("address");
            System.out.println(address);
            return address;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = JSONObject.parseObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static String readAll(BufferedReader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();

    }

}

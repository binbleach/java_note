package com.huangjiabin.site.sys.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Random;

public class CommonUtil {
    private static final int DEFAULT_PAGE_NUM = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    public static String getRandomNumCode(int number){
        String codeNum = "";
        int [] numbers = {0,1,2,3,4,5,6,7,8,9};
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int next = random.nextInt(10000);//目的是产生足够随机的数，避免产生的数字重复率高的问题
            codeNum+=numbers[next%10];
        }
        System.out.println("--------");
        System.out.println(codeNum);

        return codeNum;
    }

    public static <T> JSONArray listToJsonArray(List<T> list) {
        return JSONArray.parseArray(JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
    }

    public static <T> JSONArray listToJsonArray(List<T> list, boolean formatDate) {
        return formatDate ? JSONArray.parseArray(JSON.toJSONString(list, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue))
                : JSONArray.parseArray(JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
    }



}

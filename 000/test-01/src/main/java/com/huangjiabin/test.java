package com.huangjiabin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class test {
    /**
        测试循环中remove
    */
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "a", "b", "c");
        for (String item : list) {
            if ("b".equals(item)) {
                list.remove(item);
            }
        }
        System.out.println(list);
        System.out.println("运行结束");
    }

}

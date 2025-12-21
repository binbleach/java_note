package com.huangjiabin.yh.minifast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "a", "b", "c");
        for (String item : list) {
            if ("b".equals(item)) {
                list.remove(item);
            }
        }
    }
}

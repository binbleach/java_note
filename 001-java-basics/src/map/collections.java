package map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class collections {
    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(123);
        list.add(234);
        list.add(234);
        list.add(123);
        Collections.synchronizedList(list); //非线程安全转线程线程安全
        System.out.println(list);
        Collections.sort(list);  //不返回任何数
        /*
        如果是自定义类型也要继承Comparable接口
        如果是set集合的话要转成list集合才能用
        如：List list = new ArrayList(set);
         */
        System.out.println(list);
    }
}

package map.SortedMap.TreeMap;

import map.map;

import java.util.TreeMap;
import java.util.TreeSet;

public class treemap {
    /*
    TreeSet底层是TreeMap，TreeMap底层是二叉树
    特点是无序不可重复但是会帮你自动排序，升序
     */
    public static void main(String[] args) {
        TreeSet<String> set = new TreeSet<>();
        set.add("zhangsan");
        set.add("lisi");
        set.add("wanger");
        System.out.println(set);
        for (String s : set) {
            System.out.println(s);
        }
        TreeMap map = new TreeMap();
        map.put(1,"张三");
        map.put(1,"王二");
        map.put(2,"年末");
        map.put(3,"几位");
        for(Object a:map.values()){
            System.out.println(a);
        }
    }
}

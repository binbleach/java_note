package map.HashTalbe.Properties;

import map.map;

import java.util.Properties;

public class properties {
    //继承于HashTable
    // 存的key和value必须是String类型的
    //线程安全的 存setProperty("abc","zahngsan")，取pro.getProperty("abc")
    public static void main(String[] args) {
        Properties pro = new Properties();
        pro.setProperty("abc","zahngsan"); // 存的key和value必须是String类型的
        System.out.println(pro.getProperty("abc"));
    }
}
package com.huangjiabin.yh.minifast.a01_gc;

import java.util.Vector;

public class JavaGC {
    static Vector vector = new Vector(5);

    public static void main(String[] args) {
        //静态集合未清理过期键值对 导致内存泄露
        //test01();
        //stw的必要性
//        test02();
        System.out.println( 1 | 1);
        System.out.println( 1 | 2);
        System.out.println( 1 | 3);
        System.out.println("================");
        System.out.println( 3 | 3);
        System.out.println( 3 | 2);
        System.out.println( 3 | 1);
        System.out.println("================");
        System.out.println( 1 & 1);
        System.out.println( 1 & 2);
        System.out.println( 1 & 3);
        if(1 == 1 && 1 ==2){
            System.out.println("aaaa");
        }
        System.out.println(1 == 2 | 2 ==2);
        System.out.println(1L << 1);
    }


    //静态集合未清理过期键值对 导致内存泄露
    private static void test01(){
        for (int i = 1; i<100000; i++){
            Object object = new Object();
            vector.add(object);
            object = null;  // 仅释放引用，但 Vector 仍持有对象
        }
    }

    //stw的必要性
    private static void test02(){
        vector.add("aaa");
        Vector temp;
        temp = vector;
        vector = null;  //将vector 变量指向null，此时Context对象只被 temp 变量引用。
        //如果没有stw，Vector对象则没有GC Root引用会被清除，temp.toString()可能会报错
        System.out.println("你好，你好，你好你好你好："+temp.toString());
    }
}

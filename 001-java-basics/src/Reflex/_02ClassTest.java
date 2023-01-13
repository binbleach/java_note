package Reflex;

import java.util.Date;

public class _02ClassTest {
    /*获取.class文件*/
    public static void main(String[] args) {
        try {
            //第一种
            Class s = Class.forName("java.lang.String"); //代表String.class文件或者String类型
            Class d = Class.forName("java.util.Date");
            System.out.println("String简单名字:"+s.getSimpleName()); //简单类名
            System.out.println("Date全名:"+d);  //直接取全类名

            //第二种
            String ss = "abc";
            Class S=ss.getClass();
            Date dd = new Date();
            Class D = dd.getClass();
            System.out.println("String的全名"+S);
            System.out.println(S.equals(s));
            System.out.println(D.equals(d));

            //第三种方法
            Class sss = String.class;
            Class ddd = Date.class;
            System.out.println(S.equals(sss));
            System.out.println("sss"+sss);
            System.out.println("ddd"+ddd);
            System.out.println(Integer.class);
            System.out.println(int.class);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

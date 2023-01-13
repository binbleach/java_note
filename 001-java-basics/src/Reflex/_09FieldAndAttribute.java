package Reflex;

import java.lang.reflect.Field;

public class _09FieldAndAttribute {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        //正常的获取属性值
        field2 field2 = new field2();//创建对象
        field2.age = 21;  //给对象属性赋值
        System.out.println(field2.age); //输出赋值
        System.out.println("————————————————————————————");
        //用反射机制获取对象属性值
        Class fieldcc = Class.forName("Reflex.field2");//获取class
        Object obj = fieldcc.newInstance();//创建对象，调的是无参构造
        Field field = fieldcc.getDeclaredField("name");//通过名字获取Field字段不要返回数组 //注意如果是private修饰的不可读取
        System.out.println(field.getName());
        field.set(obj,"黄家宾");//设置字段也就是属性值
        System.out.println(field.get(obj));//获取属性值
        System.out.println(obj);
        System.out.println("————————————————————————————————————");
        //反射机制缺点：打破封装访问私有的,可能会给不法分子机会
        Field field1 = fieldcc.getDeclaredField("weight");
        field1.setAccessible(true);
        field1.set(obj,0.1f);
        System.out.println(field1.get(obj));  //得到它的属性值


    }
}

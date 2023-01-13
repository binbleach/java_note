package Reflex;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class _12invoke {
    /*用反射调用方法*/
    public static void main(String[] args) {
        //普通调用
        field2 field = new field2();
        field.acttic("abc",2);
        try {
            //反色机制调用
            Class field2Class = Class.forName("Reflex.field2");
            Object field2Object=field2Class.newInstance();
            /*可变长度参数*/
    Method field2Method =field2Class.getDeclaredMethod("acttic", String.class,int.class);//获取Method传方法名和参数类
            field2Method.invoke(field2Object,"abc",2); //调用方法，传对象和参数
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

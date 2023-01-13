package Reflex;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class _14newObject {
    public static void main(String[] args) {
        /*通过反射机制创建对象*/
        try {
            Class c = Class.forName("Reflex.field2");
            /*c.newInstance();*/  //第一种
            Constructor C=c.getDeclaredConstructor(String.class,int.class);
            field2 o=(field2) C.newInstance("黄家宾",1);
            System.out.println(o);
            o.acttic("黄家宝",2);


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

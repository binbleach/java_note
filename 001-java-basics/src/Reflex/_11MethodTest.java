package Reflex;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class _11MethodTest {
    /*最重要*/
    public static void main(String[] args) {
            try {
                StringBuilder s = new StringBuilder();
                Class fieldClass = Class.forName("Reflex.field2");
                Method[] a=fieldClass.getDeclaredMethods();
                for (Method method : a) {
                    //        修饰符编码转修饰符字符串(返回修饰符编码)        返回返回参数类型就是类  返回类的简单名
                    s.append(Modifier.toString(method.getModifiers())+" "+method.getReturnType().getSimpleName());
                    s.append(' '+method.getName()+' '+"(");
                    System.out.println(method.getParameterCount());
                    for (Class parameterType : method.getParameterTypes()) {
                        s.append(parameterType.getSimpleName()+",");
                    }
                    s.deleteCharAt(s.length()-1); //删除指定下标下的字符，把形参的多余逗号删除
                    s.append(")"+"{}");
                }
                System.out.println(s);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

    }
}

package Reflex;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class _13ConstructorTest {
    /*反射构造方法，跟普通方法差不多*/
    public static void main(String[] args) {
        StringBuilder s = new StringBuilder();
        try {
            /*01加载类*/
            Class stringclass = Class.forName("java.lang.String");
            /*02通过类调构造方法数组*/
            Constructor[] constructor = stringclass.getDeclaredConstructors();
            s.append(Modifier.toString(stringclass.getModifiers())+" "+"class"+" "+stringclass.getSimpleName()+"{");
            /*循环构造方法数组*/
            for (Constructor constructor1 : constructor) {
                s.append("\n");
                s.append("\t");
                s.append(Modifier.toString(constructor1.getModifiers())+" "+stringclass.getSimpleName()+" "+"(");

                for (Class parameterType : constructor1.getParameterTypes()) { //拿参数类型，
                  s.append(parameterType.getSimpleName()+",");
                }
                if(constructor.length>0){
                    s.deleteCharAt(s.length()-1);
                }
                s.append(")");
            }
            s.append("\n");
            s.append("}");
            System.out.println(s);
            //可以用这种方法代替纯的newInstance不是重点
            Constructor a =stringclass.getDeclaredConstructor(String.class);
            Object b =a.newInstance("abc");
            System.out.println(b);

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

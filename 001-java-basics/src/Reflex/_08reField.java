package Reflex;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class _08reField {
    public static void main(String[] args) {
        StringBuilder s = new StringBuilder();
        try {
            //通过反射拿到类的属性
            Class field2 = Class.forName("java.util.Date");
            Field[] field = field2.getDeclaredFields();
            s.append(Modifier.toString(field2.getModifiers())+" class "+field2.getSimpleName()+"{");
            s.append("\n");
            for (Field field1 : field) {
                s.append("\t"); //缩进
                s.append(Modifier.toString(field1.getModifiers()));
                s.append(" ");
                s.append(field1.getType().getSimpleName());
                s.append(" ");
                s.append(field1.getName()+";");
                s.append("\n");
            }
            s.append("}");
            System.out.println(s);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

package Reflex;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class _07field {
    public static void main(String[] args) {
        try {
            Class cfield = Class.forName("Reflex.field2");

            Field[] fields = cfield.getFields();  //这个方法拿到的是public修饰的field.
            for (Field field : fields) {
                System.out.println(field.getName()); //拿到属性名字
            }
            System.out.println("————————————————————————————————————————");
            Field[] fieldss = cfield.getDeclaredFields();//这是拿到所有修饰符修饰的Field
            for (Field field : fieldss) {
                System.out.println(field.getType().getSimpleName()); //拿到属性的类型的类然后再拿简写名
                System.out.println(field.getName());
                System.out.println(field.getModifiers());//返回的是属性的代表修饰的数字
                System.out.println(Modifier.toString(field.getModifiers()));//拿到的是修饰符private
                System.out.println("————————————————————————————————————————");
            }
            System.out.println(cfield.getTypeName());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
class field2{
    String name;
    protected int age;
    public double height;
    private float weight;

    public field2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public field2() {
    }

    public void acttic(String a, int b){
        System.out.println(a+b);
    }

    @Override
    public String toString() {
        return "field2{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}

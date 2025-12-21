package _09prototype.b;

import _09prototype.a.WeekReport;
import java.util.Date;

/*
    需求：当一个系统需要大量创建相似或完全相同的对象时，可以使用原型模式来提高性能和灵活性。
    原型模式；其实就是克隆模式，对象要实现 Cloneable接口，克隆出来的对象地址是不一样的，但是要避免浅拷贝（属性为引用类型）（可以用序列化）
    优点：减少创建新对象所需的时间和空间开销，减少代码的重复编写
 */
public class test {
    public static void main(String[] args) {
        WeekReport w1 = new WeekReport();
        w1.setId("1");
        w1.setName("黄家宾");
        w1.setWork("浑水摸鱼");
        w1.setTime(new Date());

        try {
            WeekReport w2 = w1.clone();
            w2.getTime().setTime(0);
            System.out.println(w1 == w2);
            System.out.println(w1);
            System.out.println(w2);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
}

package io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
序列化的对象必须实现Serializable接口，标志接口没有方法不过有java虚拟机特殊对待 ，serialVersionUID 序列化版本号
* 序列化对象(Serialize) ------对象就向购物车
* java对象切割一块块的有序号的从内存传到硬盘。
* 你改对象就是改序列化版本号，再次反序列化会错误需要重新序列化一次因为序列化版本号不同
* */
public class _15ObjectOutputStrem {
    public static void main(String[] args) {
        ObjectOutputStream ios =null;
        ObjectOutputStream ioss = null;
        try {
            //创建学生对象
            student a = new student("黄家宾",21);
            student b = new student("何林权",99);

            //创建序列化通道
            ios = new ObjectOutputStream(new FileOutputStream("ObjectOutputStream"));
            //序列化对象
            ios.writeObject(a); //序列化一个对象

            ioss = new ObjectOutputStream(new FileOutputStream("ObjectOutputSteams"));
            //反序列化多个对象集合 ，不能直接存多个对象，会报错得用集合
            List<student> arr = new ArrayList<student>();
            arr.add(a);
            arr.add(b);
            ioss.writeObject(arr);
            ios.flush();
            ioss.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(ios!=null){
                try {
                    ios.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(ioss!=null){
                try {
                    ioss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
/*
*建议给结继承 Serializable的类一个固定序列版本号，防止以后改了jvm不认识他
* 手动写出来，不用自动生成可以参考集合等
*java 虚拟机区分一个类先通过类名区分再通过序列版本号区分，所以时间不同的一个相同的类可能会因为序列版本号的不同
* 被判定为两个类
* */
class student implements Serializable {
    private static final long serialVersionUID = 8L;//后续代码怎么改都不怕
    String name;
    transient int age; //表示游离，则不会序列化

    public student() {
    }

    public student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

package io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class _16ObjectInputStream {
    /*
    * DeSerialize 反序列化----对象
    * */
    public static void main(String[] args) {
        ObjectInputStream oins = null;
        ObjectInputStream oinss = null;
        try {
            //反序列化单个
            oins = new ObjectInputStream(new FileInputStream("ObjectOutputStream"));
            Object a =oins.readObject();
            System.out.println(a);

            //反序列化多个对象集合 ，不能直接存多个对象，会报错得用集合
            oinss= new ObjectInputStream(new FileInputStream("ObjectOutputSteams"));
            List<student> b = (List<student>)oinss.readObject();
            for (student students : b) {
                System.out.println(students);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

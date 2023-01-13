package Reflex;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/*
* 类的根目录就是src目录
* */

public class _05path {
    public static void main(String[] args) throws FileNotFoundException {
        //FileReader read = new FileReader("src/pathtxt");//换一个软件系统如links就运行不了了;
        try {
            //直接以流的形式返回必须文件必须扩展名是properties;
            //InputStream read = Thread.currentThread().getContextClassLoader().getResourceAsStream("path.properties");
            String path = Thread.currentThread().getContextClassLoader().getResource("path.properties").getPath();
            System.out.println(path);
            FileReader read = new FileReader(path); //文件名有下划线报错了
            Properties pro = new Properties();
            pro.load(read);
            System.out.println(pro.getProperty("name"));
            read.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

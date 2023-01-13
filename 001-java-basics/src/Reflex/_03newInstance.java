package Reflex;

/*
* 通过属性调用无参构造创建对象
* 得到对象可以调用它的方法
* */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class _03newInstance {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        FileReader file = new FileReader("classInfo.properties");
        Properties pro = new Properties();
        pro.load(file);
        String classname =pro.getProperty("classname"); //java.util.Date
        Class c = Class.forName(classname);
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        Object C=c.newInstance(); //返回的是一个无参构造的对象 ,
        String a=time.format(C);
        System.out.println(a);
        file.close();
    }
}

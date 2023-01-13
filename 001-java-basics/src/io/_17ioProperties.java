package io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/*
* io流和Propertise的配合使用
*
* 像这种更改文件就能动态改变java获取到的数据的文件叫做配置文件
*
*  java程序在编写的时候，有些参数由于不确定，或者说在开发中，测试中，生产上的数据值都是不同的，比如ip地址
* 和端口，开发中url的是自己写的测试url，测试和生产环境中要变更url地址，如果这些信息写在java文件中，那就会
* 非常难以修改并且不安全，因为到后期测试中，是没有java源码的，都是编译后的class文件，你要修改系统的参数，
* 只能修改.java文件再编译成class文件再替换到测试环境中，即麻烦又不安全，所以要有配置文件，把系统重要的常
* 变的参数写在里面，在环境切换需要修改参数时就可以直接通过修改配置文件的数据来达到目的，这也是为什么一个
* 程序必须要有配置文件的原因。
*
*key=value这种的叫做属性配置文件java规范中后缀是·propertise，然后#是//然后如果key相同则会覆盖value的值
* */
public class _17ioProperties {
    public static void main(String[] args) {
        FileReader a =null;
        try {
            a = new FileReader("IoPropertise");
            Properties b = new Properties();
            b.load(a); //将文件中的数据加载到Map集合中，等号坐标是key右边是value
            System.out.println(b.getProperty("name"));
            System.out.println(b.getProperty("key"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(a!=null){
                try {
                    a.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

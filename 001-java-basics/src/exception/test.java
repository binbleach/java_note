package exception;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class test {
    public static void main(String[] args) {
        FileInputStream a =null;
        //jdk8的新特性
        try{
            a = new FileInputStream("D:\\文件\\第6章代码.txt");
            System.out.println("try的后面");
            String b = null;
            b.toString(); //空指针异常
            //a.close(); 这里的流必须关闭但是它有个IO异常所以要用到finally
            return;
        }catch(FileNotFoundException | ArithmeticException | NoClassDefFoundError e){
            System.out.println("文件没找到");
            System.out.println("catch的后面");
        }catch(NullPointerException e){
            System.out.println("空指针异常1");
        }finally {
            if(a != null) {
                try {
                    a.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        NullPointerException e = new NullPointerException("空指针异常");
        System.out.println(e.getMessage());

        e.printStackTrace(); //直接打印了 用了异步的方式
        System.out.println(Interview.m());

        try {
            NullPointerException t = new NullPointerException("空指针异常");
            //return;   加上这个最后的输出将不能被执行
            //System.exit(0);//只有这条能治finally; 这里是退出JVM
        }finally {
            System.out.println("倒数");
        }
        System.out.println("最后");

        MyException E = new MyException("我的异常");
        System.out.println(E.getMessage());
    }
}

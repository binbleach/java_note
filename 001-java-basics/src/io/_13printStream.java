package io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class _13printStream {
    //标准输出流可以指定输出位置
    public static void main(String[] args) {
        /*指定在控制台*/
        PrintStream ps = System.out;
        ps.print("我好帅");
        try {
            /*指定在文件中*/
            PrintStream to = new PrintStream(new FileOutputStream("PrintStream.txt"));
            /*标准输出方式*/
            to.print("帅你妈");
            /*指定到文件*/
            System.setOut(to);
            System.out.println("我好帅呀");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

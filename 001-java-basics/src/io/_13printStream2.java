package io;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class _13printStream2 {
    /*日志工具*/
    public static void ma(String s) {
        try {
            PrintStream a = new PrintStream(new FileOutputStream("PrintStream02.txt",true));
            System.setOut(a);
            Date time = new Date();
            SimpleDateFormat t = new SimpleDateFormat("yy-MM-dd HH-mm-ss SSS");
            String times = t.format(time); //Date转String
            System.out.println(s+times);
        }
         catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        _13printStream2.ma("系统错误");
        _13printStream2.ma("文件错误");
        _13printStream2.ma("一切都错误");
    }
}

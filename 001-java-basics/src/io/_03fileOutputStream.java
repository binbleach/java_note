package io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
文件字节输出流负责写
内存到硬盘
*/
public class _03fileOutputStream {
    public static void main(String[] args) {
        FileOutputStream fil=null;
        try {
            /* 01创建通道*/
            fil = new FileOutputStream("FileOutputStream01"); //没有文件会自动创建文件，有文件会清空内容
            /* 02获取byte型数组数据*/
            byte bytes [] = {97,98,99};
            /* 03输出数据*/
            fil.write(bytes);
            fil.write(bytes,0,2);
            /*04刷新通道*/
            fil.flush();
            fil = new FileOutputStream("FileOutputStream02",true);//有true不会清空只会追加
            String a = "我是一个中国人，我爱我的祖国";
            bytes = a.getBytes();  //得到byte型数组
            fil.write(bytes); //必须传入byte型数组

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                /*关闭通道*/
                fil.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

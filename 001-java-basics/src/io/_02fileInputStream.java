package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
    文件字节输入流，万能的任何类型的文件都能输入
    从硬盘到内存，读的
    int available()剩余可读的
    Long skip(n) 跳过n个不读
     */
public class _02fileInputStream {
    public static void main(String[] args) throws IOException {

        FileInputStream file = null;

        try {
            //file = new FileInputStream("D:\\Java\\IDEA_WorkSpece\\JavaSE Note\\javaio.txt");
            /* 01创建通道*/
            file = new FileInputStream("javaio.txt"); //相对路径，idea工具工程作为根
            int readData=0;

            //.read()类似迭代器的next()。返回的是字节如果没有数据返回-1
            /* 02读取值,读的是字节*/
           while(((readData=file.read())!=-1)){
               System.out.println(readData);
           }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(file!=null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        /*
            批量读取
            byte[] bytes = new byte[3]；
            1、readCount=fil.read(bytes) //返回的是读的字节数，
            2、bytes存储的是读到的字节。数组装满的话会从头开始覆盖。
            不能用new String(bytes)会有脏数据，要用new String(bytes,0,readCount)
            或者用fil.available()剩余可读的直接将数组长度设置为文件字节长度。
         */
        FileInputStream fil = null;
        try {
            /* 01创建输入通道*/
            fil = new FileInputStream("javaio.txt");

            Long a=fil.skip(2);//这里就跳过了两个字节了,返回Long
            System.out.println("============================");
            System.out.println(a);
            /* 02 创建byte数组*/
            int available = fil.available();
            //这里直接传new byte[available]可以不用循环了；这种方式不适合大文件因为数组不易过大
            byte[] bytes = new byte[3] ; //
            int readCount=0;
            /* 03将数据读到数组里*/
            while((readCount = fil.read(bytes))!=-1){
                /*04转换成字符输出*/
                System.out.println("------------");
                System.out.println(new String(bytes,0,readCount)); //数组转字符串
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(fil!=null){
                try {
                    fil.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}

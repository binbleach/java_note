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
        快的读取方法这里涉及两个关键点
        第一个readCount=read(byte[n]) //返回的是读的字节数，数组byte[]存储的是读到的，第二次读取如果超出数组长度
        会从头开始覆盖不过后面的元素保留
        第二个new String(byte[n])会将数组转成字符串；不够用所以new String(byte[n],0,readCount)
         */
        FileInputStream fil = null;
        try {
            /* 01创建输入通道*/
            fil = new FileInputStream("FileInputStream.txt");

            Long a=fil.skip(2);//这里就跳过了两个字节了,返回Long
            System.out.println("============================");
            System.out.println(a);

            /*
                int readCount = 0;
                if(readCount=fil.read(byte[4])!=-1){
                System.out.print(new String(bytes,0,readCount)); //读到几个转几个
                }
            }*/
            /* 02 创建byte数组*/
            byte[] bytes = new byte[fil.available()] ; //剩余可读的
            //这里直接传available可以不用循环了//这种方式不适合大文件因为数组不易过大
            int readCount=0;
            /* 03将数据读到数组里*/
           while((readCount = fil.read(bytes))!=-1){
               /*04转换成字符输出*/
               System.out.print(new String(bytes,0,readCount)); //数组转字符串
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

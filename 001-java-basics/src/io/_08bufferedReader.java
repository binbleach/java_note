package io;

import java.io.*;

public class _08bufferedReader {
    public static void main(String[] args) {
        /*
        * FileReader fil = new FileReader("copy"); //节点流
        BufferedReader file = new BufferedReader(fil); //包装流也叫处理流 //关闭时只关闭最外层就好
        * */
        BufferedReader buf = null;
        try {
            /*01建通道*/
            buf = new BufferedReader(new FileReader("BufferedReader.txt"));
            /*String a=buf.readLine(); //读一行
            System.out.println(a);*/
            String  b= null;
            while((b = buf.readLine())!=null){
                System.out.println(b);  //不带换行符
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(buf!=null){
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

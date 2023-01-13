package io;

public class _01test {
    public static void main(String[] args) {
        /*
        java.io下的
        四大家族首领：(都是抽象类)
        InputStream字节输入流
        OutputStream字节输出流
        Reader字符输入
        Writer字符输出
        以Stram结尾的都是字节流，可以读任何文件，一次读一个byte 8个字节
        以Reader或者Writer结尾的都是字符流，只能读文本文件，一次读一个字符
        都有实现接口Cloesable(可关闭的) Cloesable有cloes()方法;流是一个管道，用完流关闭。
        所有输出流实现刷新Flushable接口 都有flush()方法;用完输出流刷新，用于清空管道强行输出。没有可能会丢失数据

        需要掌握的流共有16个：
        文件专属
        java.io.FileInputStream  //字节输入流
        java.io.FileOutputStream  //字节输出流
        java.io.FileReader        //字符输入流
        java.io.FileWriter         //字符输出流

        转换流(将字节流转换为字符流)
        java.io.InputStreamReader
        java.io.OutputStreamWriter

        缓冲流专属
        java.io.BufferedInputStream
        java.io.BufferedOutputStream
        java.io.BufferedReader
        java.io.BuffereWriter

        数据流专属
        java.io.DataInputStream
        java.io.DataOutputStream

        标准输出流
        java.io.PrintStream
        java.io.PrintWriter

        对象专属流
        java.io.ObjectInputStream
        java.io.ObjectOutputStream

         */
    }
}

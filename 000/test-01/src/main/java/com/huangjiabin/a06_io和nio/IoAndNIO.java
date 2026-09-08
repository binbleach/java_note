package com.huangjiabin.a06_io和nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;

/*
 *   nio：java1.4引入的io模型，非阻塞式io。（nio的n是non-blocking缩写。）
 *       1、非阻塞：指一次能读多少就返回多少，不像传统IO读不到指定长度内容就不返回
 *       2、多路复用：selector可以注册多个channel，一个线程同时监控多个通道
 *       3、buffer缓冲区，可以临时存储数据提高效率。channel双向通道，可以同时读和写。
 *   差异：餐馆点菜，点餐出餐后才能下一位的是bio。点餐后顾客主动问是否出餐的是nio。点餐后主动告诉顾客是否出餐的是aio。
 *   bio：面向流，     阻塞io（等待返回），             一个连接一个线程，适合连接数目较少且固定的架构
 *   nio：面向缓冲区， 同步非阻塞（立即返回，主动获取）， 一个线程同时监控多个通道（epoll多路复用），适合连接数目多
 *   aio：面向事件，  异步非阻塞（立即返回，回调通知）。  适合处理大量并发连接
 */
public class IoAndNIO {
    public static void main(String[] args) {
        io();
        nio();
    }

    public static void nio() {
        // NIO读取文件
        try (FileChannel channel = FileChannel.open(Paths.get("javaio.txt"))) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 非阻塞读取：立即返回，不等待
            while (channel.read(buffer) != -1) {
                buffer.flip();  // 切换为读模式
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                }
                buffer.clear(); // 清空缓冲区
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("你好");
    }

    public static void io() {
        // 传统IO读取文件
        try (FileInputStream fis = new FileInputStream("javaio.txt")) {
            int data;
            // 阻塞读取：线程会一直等待，直到数据可用
            while ((data = fis.read()) != -1) {
                System.out.print((char) data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

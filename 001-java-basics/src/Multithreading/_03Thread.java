package Multithreading;

import java.util.ArrayList;
import java.util.LinkedList;

public class _03Thread {
    public static void main(String[] args) {
        //新建一个线程对象
        MyThread myThread = new MyThread();

        //启动线程
        //start方法的作用是启动一个线程，在JVM中开辟一个栈空间，这段代码完成后就瞬间结束了，线程启动成功
        //线程启动成功会自动调用run方法，同时run在分支栈的底部和main同级
        myThread.start();
        //如果直接调用myThread.run不会并发,在同一个栈中得等到run()方法结束才往下运行
        //还是自上而下的运行定律(永远不变)，不过start()瞬间结束所以并发
        for(int i=0;i<1000;i++){
            System.out.println("主线程-->"+i);
        }
    }
}

class MyThread extends Thread{
    @Override
    public void run() {
        super.run();
        for(int i=0;i<1000;i++){
            System.out.println("分支线程-->"+i);
        }
    }
}

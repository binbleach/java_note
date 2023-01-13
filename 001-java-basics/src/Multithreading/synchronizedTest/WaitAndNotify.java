package Multithreading.synchronizedTest;

import java.util.ArrayList;
import java.util.List;

/*
* 生产消费必须均衡。
* Object.wait();
* Object.notify();
* */
public class WaitAndNotify {
    public static void main(String[] args) {
        List list = new ArrayList();
        Thread producer = new Producer(list); //生产线程
        Thread consumer = new Consumer(list); //消费线程
        producer.setName("生产者");
        consumer.setName("消费者");
        producer.start();
        consumer.start();

    }
}
class Producer extends Thread{
    List list;
    Producer(List list){
        this.list=list;
    }
    @Override
    public void run() {
        while (true){
            synchronized (list) {
                if (list.size() > 0) {
                    try {
                        list.wait(); //进入等待释放锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Object obj = new Object();
                list.add(obj);
                list.notify();
                System.out.println("生产者生产一个-->>"+obj);
            }
        }
    }
}
class Consumer extends  Thread{
    List list;
    Consumer(List list){
        this.list=list;
    }
    @Override
    public void run() {
        while (true){
            synchronized (list) {
                if (list.size() == 0) {
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Object obj = new Object();
                list.remove(0);
                list.notify();
                System.out.println("消费者消费一个-->>"+obj);
            }
        }
    }
}
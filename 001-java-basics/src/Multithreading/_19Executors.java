package Multithreading;

import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/*
    线程池好处：
        1、提高速度（减少创建新线程的时间）
        2、降低资源消耗（重复利用线程池中的线程，不需要每次都创建）
        3、便于线程管理：设置属性（接口不能调得用实现类）
            carePoolSize：核心池大小
            maximumPoolSize：最大线程数
            keepAliveTime：线程没有任务时最多保持多长时间后会终止

    Executors：工具类、线程池的工厂类，用于创建并返回不同类型的线程池
        newCachedThreadPool():创建一个可根据需要创建新线程的线程池
        newFixedThreadPool(n):创建一个可重复用固定线程数的线程池
        newSingleThreadExecutor():创建一个只有一个线程的线程池
        newSchedduledThreadPool(n):创建一个线程池，它可以安排在给定延迟后运行


*/
public class _19Executors {
    public static void main(String[] args) {
        //01创建一个线程池
        ExecutorService service=Executors.newFixedThreadPool(10);

        //转成实现类,用于调属性
        ThreadPoolExecutor service1 = (ThreadPoolExecutor) service;
        service1.setCorePoolSize(10);

        //02执行指定线程的操作，需要提供实现Runnable或Callable接口实现类的对象
        service1.execute(new NumberThread());//没有返回值适合使用于Runable
        service1.execute(new NumberThread1());//适合使用于Runable
        //service.submit();//有返回值，适合适用于callable

        //03关闭线程池
        service1.shutdown();
    }
}
class NumberThread implements Runnable{

    @Override
    public void run() {
        for(int i=0;i<100;i++){
            if(i%2==0){
                System.out.println(Thread.currentThread().getName()+":"+i);
            }
        }
    }
}
class NumberThread1 implements Runnable{

    @Override
    public void run() {
        for(int i=0;i<100;i++){
            if(i%1==0){
                System.out.println(Thread.currentThread().getName()+":"+i);
            }
        }
    }
}

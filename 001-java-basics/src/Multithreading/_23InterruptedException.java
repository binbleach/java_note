package Multithreading;

import java.util.concurrent.*;

/*
*   1、主线程InterruptedException异常，不是future任务中断才抛出，而是get()或者sleep()这种有阻塞的方法遇到主线程中断标记interrupt()而导致。
*   2、主线程InterruptedException异常后futrue任务继续执行，除非future.cancel(true)中断标记才会去尝试中断任务。
*   3、futrue任务被future.cancel(true)中断标记，如果任务有阻塞sleep()则任务中断抛出异常，没有阻塞只是执行时间久点则不会中断。
*   4、futrue任务异常不会影响主线程，但是如果主线程get()则会抛出ExecutionException异常
*/
public class _23InterruptedException {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            System.out.println("任务开始执行");
//            try {
//                if(1 == 1){
//                    throw new NullPointerException();   //模拟future任务异常
//                }
//                for (int i =0;i<100000;i++){
//                    System.out.print(i+"："+Thread.currentThread().isInterrupted());
//                }
//                Thread.currentThread().interrupt();
//                Thread.sleep(2000);  // 模拟futrue任务阻塞
                System.out.println("任务完成");
//            } catch (InterruptedException e) {
//                System.out.println("任务线程被中断");
//            }
        });
        try {
            Thread.sleep(3);
            Thread.currentThread().interrupt();
//            Thread.sleep(10);
            future.get();  // 这里会抛出InterruptedException
        } catch (InterruptedException e) {
            // 但任务线程还在sleep，没有被打断！
            System.out.println("主线程等待结果时被中断1");
            // 如果不调用future.cancel(true)，任务会继续执行完
            future.cancel(true);
            System.out.println("主线程等待结果时被中断2");
        }
        catch (ExecutionException e) {
            System.out.println("111");
        }
    }
}

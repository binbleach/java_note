package Multithreading;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
    0、中断+阻塞 => InterruptedException
    1、Thread.currentThread().interrupt()：
        设置中断标记，如果线程处于阻塞状态（例如处于sleep, wait, join 等状态）会抛出 InterruptedException异常，同时JVM会清除中断标记。
        Thread.currentThread().isInterrupted()：判断是否出于中断状态
        Thread.interrupted()：判断是否出于中断状态，并清除中断状态
    2、future.get()：
        1）future任务阻塞，get()会使得主线程的线程进入阻塞，若主线程处于中断状态会抛出 java.lang.InterruptedException异常
        2）future任务异常，get()会向主线程抛出异常如：java.util.concurrent.ExecutionException（主）: java.lang.InterruptedException（子）: sleep interrupted
    3、抛出 InterruptedException 后，当前线程的中断状态会被清除（这是Java中断机制的一部分）。因此，如果你想要在捕获 InterruptedException
       后继续处理中断（例如，重新设置中断状态或进行其他清理工作），你需要再次调用 Thread.currentThread().interrupt();
    4、future.cancel() ：
        1、若调用 cancel时任务尚未开始执行（处于等待队列中），则任务会被标记为已取消，不会执行。
        2、若参数为true，且任务已经开始，会尝试中断正在执行任务的线程（即子线程调用 interrupt() 方法）
        注：executor.submit()任务提交后并不是马上执行，所以即使future.cancel(false)代码在后仍可能取消执行该任务
*/
public class _22TEST {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> {
//            Thread.currentThread().interrupt();   //模拟子线程中断
            System.out.println("子线程开始");
            for(int i = 0 ;i<=1000 ; i++){
//                System.out.println("子线程执行进展："+i);
                Thread.sleep(1); // 模拟子线程阻塞
            }
            System.out.println("子线程结束");
            return "Done";
        });
        // 在主线程中等待结果
        try {
            Thread.sleep(1);
            Thread.currentThread().interrupt();   //模拟主线程中断
            System.out.println("主线程当前中断状态1："+Thread.currentThread().isInterrupted());
            String result = future.get(); // 阻塞等待
            System.out.println("主线程当前中断状态2："+Thread.currentThread().isInterrupted());
            System.out.println("主线程当前中断状态3："+Thread.interrupted());

            System.out.println("结果："+false);
        } catch (InterruptedException | ExecutionException e) {
            // 这里表示：主线程在等待时被中断了！
            // 但任务线程仍在运行（除非显式取消）
            System.out.println("主线程等待时被中断："+e);
            future.cancel(true);
            System.out.println("主线程当前中断状态4："+Thread.currentThread().isInterrupted());
        }
        System.out.println("主线程结束=============");
    }
}

package Multithreading;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

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
public class _21TEST {

    public static ThreadPoolExecutor threadPool =
            new ThreadPoolExecutor(20, 50, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50000),
                    Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        runAsyncTimeout(()->{
            test();
        },3,countDownLatch);

        try {
            boolean success = countDownLatch.await(3, TimeUnit.SECONDS);
            if (!success) {
                System.out.println("超时");
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            System.out.println("最外层异常");
        }
    }
    static void test(){
        Integer[] ints = new Integer[6];
        Arrays.setAll(ints, i -> i);
        Map<String,Future> futureMap = new HashMap<>();
        for(Integer i:ints){
            if(futureMap.keySet().size()>5){
                System.out.println("上线程数量："+futureMap.keySet().size());
                for(String key:futureMap.keySet()){
                    try {
                        Object o = futureMap.get(key).get();
                        System.out.println("上执行结果："+o.toString());
                    } catch (InterruptedException | ExecutionException e) {
                        futureMap.get(key).cancel(true);
                        /*
                            如果线程处于被阻塞状态（例如处于sleep, wait, join 等状态），那么线程将立即退出被阻塞状态，
                            并抛出一个InterruptedException异常。注：future.get()时线程未完成才算阻塞，完成了的不算
                        */
                        Thread.currentThread().interrupt();
                        System.out.println("上线程异常："+key);
//                        throw new NullPointerException();
                    }
                }
                futureMap = new HashMap<>();
            }
            Future<?> future = threadPool.submit(() -> {
                try {
                    System.out.println("线程开始："+i+"------>"+Thread.currentThread().getName());
                    Thread.sleep(3000);
                    System.out.println("线程执行："+i+"------>"+Thread.currentThread().getName());
                } catch (Exception e) {
                    System.out.println("上睡眠异常："+i+e.getMessage());
                }
//                if(i == 0){
//                    try {
//                        Thread.sleep(3);
//                    } catch (Exception e) {
//                        System.out.println("中睡眠异常："+i+e.getMessage());
//                    }
//                }
//                if(i == 2){
//                    try {
//                        Thread.sleep(3);
//                    } catch (Exception e) {
//                        System.out.println("中睡眠异常："+i+e.getMessage());
//                    }
//                }
                if(i == 4){
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        System.out.println("中睡眠异常："+i+e.getMessage());
//                        throw new NullPointerException();
                    }
                    for(int a =0;a<100000000;a++){
                        for(int b =0;b<1000000;b++){
                            if(a == 99999999 && b == 999999){
                                System.out.println("0000000");
                            }
                        }
                    }
                }
                if(i ==2){
                    System.out.println("异常开始："+i);
                    throw new NullPointerException();
                }
//                try {
//                    Thread.sleep(3000);
//                } catch (Exception e) {
//                    System.out.println("下睡眠异常："+i+e.getMessage());
//                }
                System.out.println("线程结束："+i+"------>"+Thread.currentThread().getName());
                return i;
            });
            System.out.println("iiiii"+i);
            futureMap.put(i.toString(),future);
        }
        System.out.println("下线程数量"+futureMap.keySet().size());
        for(String key:futureMap.keySet()){
            try {
                Object o = futureMap.get(key).get();
                System.out.println("下执行结果："+o.toString());
            } catch (InterruptedException | ExecutionException e) {
                boolean cancel = futureMap.get(key).cancel(true);
                System.out.println("下线程异常："+key);
                System.out.println("future状态："+key+cancel);
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void runAsyncTimeout(Runnable task, long timeoutSeconds, CountDownLatch countDownLatch) {
        threadPool.execute(() -> {
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            Future<?> future = executorService.submit(task);
            try {
                future.get(timeoutSeconds, TimeUnit.SECONDS);
            } catch (TimeoutException | InterruptedException | ExecutionException | RuntimeException e) {
                System.out.println("异常了...");
                future.cancel(true);
                Thread.currentThread().interrupt();
            } finally {
                countDownLatch.countDown();
                executorService.shutdown();
            }
        });
    }

    void test2(){
        System.out.println("线程开始状态1"+Thread.currentThread().isInterrupted());
        // 为线程打中断标记
//        Thread.currentThread().interrupt();
        // 当前线程状态是否中断，上一行打中断标记后就是true
        System.out.println("线程打中断标记状态2"+Thread.currentThread().isInterrupted());
        //获取线程中断状态true，并清除
        boolean interrupted = Thread.interrupted();
        System.out.println("中断标记"+interrupted);
        // 清除完中断标记，中断状态false
        System.out.println("线程清除中断标记后状态3"+Thread.currentThread().isInterrupted());
        // 线程阻塞；线程中断过，这里就不会阻塞，继续往下执行
        LockSupport.park();
        boolean interrupted2 = Thread.interrupted();
        System.out.println("继续运行"+interrupted2);
    }
}

package Multithreading;

import java.util.concurrent.*;


public class ThreadPoolUtil {

    /**
     * 线程池阻塞队列
     */
    private static final ArrayBlockingQueue<Runnable> threadQueue = new ArrayBlockingQueue<>(100000);

    /**
     * 默认创建一个拥有20个核心线程，最大能容入50个线程的线程池
     */
    public static ThreadPoolExecutor threadPool =
            new ThreadPoolExecutor(10, 100, 30, TimeUnit.SECONDS, threadQueue,
                    Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());


    /**
     * 用于多个任务提交-有countDownLatch
     * 不会阻塞主线程
     */
    public static void runAsyncTimeout(Runnable task, long timeoutSeconds, CountDownLatch countDownLatch) {
        //开一个线程，用于超时取消。（只用一个线程做超时取消会阻塞主线程，例如：runAsyncTimeoutAwait）
        threadPool.execute(() -> {
            //新建线程防止threadPool线程耗尽导致死锁
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            //用future为了设置超时时间
            Future<?> future = executorService.submit(task);
            try {
                future.get(timeoutSeconds, TimeUnit.SECONDS);
            } catch (TimeoutException | InterruptedException | ExecutionException | RuntimeException e) {
                future.cancel(true);
                Thread.currentThread().interrupt();
            } finally {
                countDownLatch.countDown();
                executorService.shutdown();
            }
        });
    }


    /**
     * 1、将主线程阻塞等待所有子线程执行完成
     * 2、对线程的业务异常进行处理
     */
    public static void threadAwait(String timeoutMessage, CountDownLatch countDownLatch, long timeout, TimeUnit timeUnit, ThreadExceptionVo threadExceptionVo) {
        try {
            boolean success = countDownLatch.await(timeout, timeUnit);
            if (!success) {
                throw new RuntimeException(timeoutMessage);
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(exception.getMessage());
        }
        if (threadExceptionVo.isException()) {
            throw new RuntimeException(threadExceptionVo.getMessage());
        }
    }

    /**
     * 用于单个任务提交-没有countDownLatch
     * 不会阻塞主线程
     */
    public static void runAsyncTimeout(Runnable task, long timeout, TimeUnit timeUnit) {
        threadPool.execute(() -> {
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            Future<?> future = executorService.submit(task);
            try {
                future.get(timeout, timeUnit);
            } catch (TimeoutException | InterruptedException | ExecutionException | RuntimeException e) {
                future.cancel(true);
                Thread.currentThread().interrupt();
                throw new RuntimeException("执行异常：" + e.getMessage());
            } finally {
                executorService.shutdown();
            }
        });
    }


    /**
     * 单个任务提交
     * 会将主线程等待任务执行完成
     */
    public static void runAsyncTimeoutAwait(Runnable task, long timeout, TimeUnit timeUnit) {
        Future<?> future = threadPool.submit(task);
        try {
            future.get(timeout, timeUnit);
        } catch (TimeoutException | InterruptedException | ExecutionException | RuntimeException e) {
            future.cancel(true);
            Thread.currentThread().interrupt();
            throw new RuntimeException("执行异常：" + e.getMessage());
        }
    }
}

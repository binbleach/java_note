package Multithreading;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Function;

/*
    0、中断+阻塞 => 会抛出java.lang.InterruptedException异常，同时JVM会自动清除中断标记
    1、Thread.currentThread().interrupt()：设置中断标记。
       阻塞状态：处于sleep, wait, join 等状态
       Thread.currentThread().isInterrupted()：判断是否出于中断状态。
       Thread.interrupted()：判断是否出于中断状态，并清除中断状态。
    2、future.get()：
        1）get()时任务没执行完，会使得主线程的线程进入阻塞，若主线程处于中断状态会抛出 InterruptedException异常
        2）get()时任务有异常，任务会向主线程抛出异常如：java.util.concurrent.ExecutionException（主）
    3、抛出 InterruptedException 后，当前线程的中断状态会被清除（这是Java中断机制的一部分）。
        因此，如果你想要在捕获 InterruptedException后继续处理中断（例如，上层阻塞等待中，让其感知处理），
        你需要再次调用 Thread.currentThread().interrupt()。
    4、future.cancel() ：
        1、若调用 cancel时任务尚未开始执行（处于等待队列中），则任务会被标记为已取消，不会执行。
        2、若参数为true，且任务已经开始，会尝试中断正在执行任务的线程（即子线程调用 interrupt() 方法）
        注：executor.submit()任务提交后并不是马上执行，所以即使future.cancel(false)代码在后仍可能取消执行该任务
*/
public class _21TEST {

}

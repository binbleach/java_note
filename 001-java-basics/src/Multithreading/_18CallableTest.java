package Multithreading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class _18CallableTest {
    /*
    * 第三种设置线程的方式，可以获取返回值
    * */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*创建一个未来任务类对象*/
        FutureTask f = new FutureTask(new Callable() { //java8的新特新
            @Override
            public Object call() throws Exception {    //call方法相当于run方法，不过有返回值
                System.out.println("Call method begin");
                int a=100;
                int b=3;
                Thread.sleep(1000*10);
                System.out.println("call method over");
                return a*b;
            }
        });

        Thread t =new Thread(f);
        t.start();
        /*会导致main方法受阻*/
        Object obj =f.get();//与之前的 extends Thread 和 implements Runnable 的区别是可以获取到线程的返回值
                            //相对的它的效率会慢，因为要等get拿到返回值，main方法就会阻塞。等待它执行完才去执行
        System.out.println("main  method over"+obj);

    }
}

package Multithreading;
/*
*终止线程睡眠
* 因为不是静态的和谁调的有关
* 用的是java异常机制强行唤醒
* stop()过时不建议用，容易丢失数据
* */
public class _10interrupt {
    public static void main(String[] args) {
        myThread3 mythread = new myThread3();
        mythread.start();
        try {
            mythread.sleep(1000*1); //这里睡的还是main()
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mythread.interrupt();//中断线程休眠进入异常 //醒来的却是run 因为不是静态的
        System.out.println("main-->end");
    }
}
class myThread3 extends Thread{

    @Override
    public void run() {
        System.out.println("myRunnable-->"+"开始");
        try {
            Thread.sleep(1000*60*60*24*365);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("myRunnable-->结束");
    }
}
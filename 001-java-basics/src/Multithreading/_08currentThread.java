package Multithreading;
/*
* Thread.currentThread 获取当前线程对象 静态的方法
* setName设置线程对象名字
* getName获取线程对象名字
*
* */
public class _08currentThread {
    public static void main(String[] args) {
        Thread t = new Thread1();
        t.setName("t1");
        t.start();
        Thread t2 = new Thread1();
        t2.setName("t2");
        t2.start();
        for(int i=0;i<10;i++){
            System.out.println(Thread.currentThread().getName()+"-->"+i);
        }
    }
}
class Thread1 extends Thread{
    @Override
    public void run() {
        //出现在哪获取的就是哪的线程对象
        Thread currentThread= Thread.currentThread();
        for(int i=0;i<10;i++){
            System.out.println(currentThread.getName()+"-->"+i);
        }
    }
}

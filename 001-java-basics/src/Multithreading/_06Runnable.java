package Multithreading;

public class _06Runnable {
    public static void main(String[] args) {
        Thread mythread = new Thread(new myThread2());//02 将可运行的对象封装成线程对象。A.new 出来的是新建状态

        /*03 开辟栈空间，调度run()方法*/
        mythread.start();
        //线程的生命周期
        //B.start后是就绪状态又叫可运行状态，该状态表示当前线程具有抢夺cpu时间片的权利
        //C.cpu时间片也就是执行权，抢到了就执行run方法就是运行状态
        //时间片用完就会但会到就绪状态，继续抢。然后继续执行
        //D.run结束就到死亡状态
        //当一个线程遇到阻塞时间如用户键盘输入和sleep会进入阻塞状态，线程会放弃占有的时间片
        //阻塞接触会变到就绪状态继续抢夺cpu时间片
        for(int i=0;i<1000;i++){
            System.out.println("主线程-->"+i);
        }
    }
}
class myThread2 implements Runnable{/* 01还不是线程充其量是可运行的类*/

    @Override
    public void run() {
        for(int i=0;i<1000;i++){
            System.out.println("分线程-->"+i);
        }
    }
}

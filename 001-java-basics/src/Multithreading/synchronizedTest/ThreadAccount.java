package Multithreading.synchronizedTest;

public class ThreadAccount implements Runnable {
    Account zg;

    public ThreadAccount(Account zg) {
        this.zg = zg;
    }

    @Override
    synchronized public void run() { //这里对象锁不住，因为new了两个ThreadAccount有两把锁
        /*synchronized (this.zg){ //这里线程同步就解决了线程不安全的问题了，一个对象一个锁，百个对象百个锁
                                //一个类一个锁百个类也是一个锁。这里还有扩大了范围会变慢
        zg.withdrow(5000);}*/
        zg.withdraw(5000);
    }
}

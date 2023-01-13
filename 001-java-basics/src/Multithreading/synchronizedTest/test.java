package Multithreading.synchronizedTest;

//因为共享内存、并发导致两个人如果同时往账户里取钱有可能会取了10000还剩5000，这就是线程不安全，就有了同步

//其实说是锁与其说是钥匙更好理解，一个对象一个锁，看到synchronized就去锁池里找锁找到就能进
//运行状态->找锁并放弃强到的时间片，找到->就绪状态

//局部变量栈中 ，永远不会发送线程安全问题因为不共享
//实例变量堆中
//静态变量方法区中

//类锁是加在静态方法上的

public class test {
    public static void main(String[] args) {
        Account acc_1 = new Account("黄家宾",11000);
        System.out.println(acc_1.getName()+"账号余额还有"+acc_1.getBalance());
        Thread t = new Thread(new ThreadAccount(acc_1));
        Thread t2 = new Thread(new ThreadAccount(acc_1));
        t.start();
        t2.start();
    }
}

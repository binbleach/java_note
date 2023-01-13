package Multithreading;

public class _16setDeamon {
    /*守护线程就是后台线程其他是用户线程
    守护线程是一个死循环，所有的用户线程结束守护线程也就结束
    t.setDaemon(true);//将线程设置为守护线程
    * */
    public static void main(String[] args) {
        Thread t = new Thread(new Deamon());
        t.setDaemon(true);//将Deamon设置为守护线程当用户线程结束守护线程也结束
        t.start();
        for (int i =0;i<10;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"-->"+i);
        }
    }
}
class Deamon implements Runnable{
    int i =0;
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("我是守护线程-->" + (i++));
        }
    }
}

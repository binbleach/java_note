package Multithreading;

public class _13join {
    /*
    *不是重点了解就行
    * join()合并线程，多线程变成单线程
    * */
    public static void main(String[] args) {
        Thread t =new Thread(new Processor());
        t.setName("t1");
        t.start();
        for(int i=0;i<10;i++){
            try {
                t.join(); //t线程执行完毕 主线程才开始执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"---->"+i);
        }

    }
}
class Processor implements Runnable{

    @Override
    public void run() {
        for(int i=0;i<10;i++){
            System.out.println(Thread.currentThread().getName()+"---->"+i);
        }
    }
}

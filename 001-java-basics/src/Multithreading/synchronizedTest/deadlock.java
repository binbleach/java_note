package Multithreading.synchronizedTest;

public class deadlock {
    /*死锁
    * 跑的快的就正常
    * 先锁住了o1
    * 这里o2得锁住才能释放o1的，不过o2被下面的线程占了
    * */
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 =new Object();
        Thread t =new d1(o1,o2);
        Thread t2 =new d2(o1,o2);
        t.start();
        t2.start();

    }
}
class d1 extends Thread{
    Object o1;
    Object o2;
    d1(Object o1,Object o2){
        this.o1=o1;
        this.o2=o2;
    }
    @Override
    public void run() {
      synchronized (o1){ //先锁住了o1
          try {
              Thread.sleep(1000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          synchronized (o2){  //这里o2得锁住才能释放o1的，不过o2被下面的线程占了
              System.out.println("end");
          }
      }
    }
}
class d2 extends Thread{
    Object o1;
    Object o2;
    d2(Object o1,Object o2){
        this.o1=o1;
        this.o2=o2;
    }
    @Override
    public void run() {
        synchronized (o2){
            synchronized (o1){

            }
        }
    }
}
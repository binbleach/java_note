package Multithreading;

public class _11Stop {
    //终止线程不是终止线程睡眠
    //stop()过时不建议用，容易丢失数据
    //设置属性通过属性控制run的状态
    public static void main(String[] args) {
        MyThread1 thread = new MyThread1();
        thread.setName("tt");
        thread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.run=false;
    }

}
class MyThread1 extends Thread {
    boolean run=true;
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            if(run){
                try {
                    System.out.println(Thread.currentThread().getName()+"--->"+i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                return;
            }
        }
    }

}

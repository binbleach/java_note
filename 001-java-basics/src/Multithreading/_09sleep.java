package Multithreading;
/*
* Thread.sleep(Long millis) 静态方法 在哪哪睡和谁调的无关
* 传入的是一个毫秒数，让线程进入阻塞状态，进入休眠，放弃抢夺的cpu时间片
* */
public class _09sleep {
    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            try {
               Thread.sleep(1000*1);//静态方法让当前线程休眠
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
    }
}


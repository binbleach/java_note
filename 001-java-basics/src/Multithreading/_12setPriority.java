package Multithreading;

public class _12setPriority {
    /*
    * 不是重点了解就行
    * t.setPriority(10);设置线程优先级
    * t.getPriority(); //得到优先级,1最低 默认5 ，10最高
    * Thread.yield(); //让位到了就让从运行状态回到就绪状态,静态方法
    * */
    public static void main(String[] args) {
        System.out.println("advafv"+Thread.MAX_PRIORITY);
        System.out.println("avaer"+Thread.NORM_PRIORITY);
        System.out.println("avdsdas"+Thread.MIN_PRIORITY);
        Thread.currentThread().setPriority(1);
        Thread3 t =new Thread3();
        t.setName("t1");
        t.setPriority(10);
        t.start();
        for(int i=0;i<1000;i++){
            System.out.println(Thread.currentThread().getName()+"--->"+i);
        }
        System.out.println(t.getPriority()); //得到优先级,1最低 默认5 ，10最高
    }
}
class Thread3 extends Thread{
    @Override
    public void run() {
        for(int i=0;i<1000;i++){
            if(i%50 ==0){
                Thread.yield(); //让位到了就让从运行状态回到就绪状态,静态方法
            }
            System.out.println(Thread.currentThread().getName()+"--->"+i);
        }
    }
}

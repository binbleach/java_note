package Multithreading;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class _17Timertest {
    /*
    * 少用了，高级框架有自己的定时器，不过底层原理还是这个
    */
    public static void main(String[] args) {
        TimerTask t = new MyTimerTask();
        Timer tr = new Timer();  //新建一个定时器
        /*Timer tr = new Timer(true);  新建一个定时器 true代表设置为守护线程*/
        String a ="2021-7-7 18:15:00 0";
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

        try {
            Date times = time.parse(a);
            //指定定时任务 t定时对象，times第一次执行时间，每隔多久执行一次,如果时期过了则直接执行
            tr.schedule(t,times,2000);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
//继承定时器，定时器实现了Runnable接口，也是一个线程，TimerTask抽象类不能new
class MyTimerTask extends TimerTask {

    @Override
    public void run() {
        SimpleDateFormat a = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss SSS");
        Date b = new Date();
        String t=a.format(b);

        System.out.println("这是备份"+t);
    }
}

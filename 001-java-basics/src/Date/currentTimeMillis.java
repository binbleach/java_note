package Date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class currentTimeMillis {
    public static void main(String[] args) {
        Date b = new Date(100000000);//自1970年1月1日过的毫秒数的北京时间8：00,
        System.out.println(b);
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String times = time.format(b); //Date转String
        System.out.println(b.getTime());
        System.out.println(times);

        Long a = System.currentTimeMillis(); //自1970年到现在毫秒数返回的是一个long
        System.out.println(a);
        System.out.println(a/1000/60/60/24/365);
        Long begin = System.currentTimeMillis();
        print();
        Long end = System.currentTimeMillis();
        System.out.println("消耗总毫秒数："+(end-begin));

    }
    public static void print(){
        for(int i=0;i<1000;i++){
            System.out.println("i="+i);
        }
    }
}

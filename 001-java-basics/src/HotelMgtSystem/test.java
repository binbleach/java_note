package HotelMgtSystem;

import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Hotel a = new Hotel();
        System.out.println("1.查询房间信息        2.预定房间");
        System.out.println("3.退房                4.退出系统");
        while (true){
            System.out.println("请输入执行功能的编号");
            Scanner b = new Scanner(System.in);
            int c = b.nextInt();
            if(c==1){
                a.look();
            }else if(c==2){
                System.out.println("输入预定房间编号");
                Scanner yu = new Scanner(System.in);
                int di = yu.nextInt();
                a.yuding(di);
            }else if(c==3){
                System.out.println("输入退的房间编号");
                Scanner yu = new Scanner(System.in);
                int di = yu.nextInt();
                a.tuifang(di);
            }else if(c==4) return;

        }
    }
}

package base;
import java.util.Scanner;
public class ScannerTest {
    public static void main(String[] args) {
        System.out.println("1和2猜一个数：");
        Scanner num = new Scanner(System.in);
        int a=(int)(Math.random()*2+1);
        if(a==num.nextInt()){
            System.out.println("你猜对了");

        }else{
            System.out.println("你猜错了,答案"+a);

        }
    }
}

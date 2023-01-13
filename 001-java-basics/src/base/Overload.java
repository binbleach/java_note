package base;

public class Overload {
    //只看参数类型和个数方法重载；
    public static void main(String[] args) {

        int a=add(2,3);
        int b=add(2,3,4);
        float c=add(1,2);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
    public static int add(int num,int num2){
        return num+num2;
    }
    public static int add(int num, int num2, int num3){
        return num+num2+num3;
    }
    public static float add(float num,int num2) {
        return num + num2;
    }
}

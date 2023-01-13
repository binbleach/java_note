package Reflex;
/*
* 可以传数组，可以看作是数组,可以循环遍历
* 放在最后一个，所以只能有一个可变长度数组，
* */

public class _10Variable {
    public static void main(String[] args) {
        m1("abc","sacd");
        m1("我","是","中","国","人");
    }
    public static void m1(String... a){
        System.out.println("wasdv");
        for (String s : a) {
            System.out.println(s);
        }
    }
}

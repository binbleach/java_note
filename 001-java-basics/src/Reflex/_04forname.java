package Reflex;

public class _04forname {
    public static void main(String[] args) {
        /*静态代码块在类加载时执行，且只执行一次
        * 如果只希望静态代码块执行，可以用这个.forName jdbc会用到
        * */
        try {
            Class al = Class.forName("Reflex.eul");//直接让它的静态代码块执行
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
class eul{
    static{
        System.out.println("我是第一个eul");
    }
}

package Class;

public class OO {
    public static String n = "n";
    public static final String y = "y";
    public static O o = new O();
//    public static final O oo = new O();
//    public static O ooo;
//    public static final O oooo = null;
//    public A a = new A();
    public A aa;
    static {
        System.out.println("OO...静态代码块被执行！！！");
    }
    public OO (){
        System.out.println("OO...被创建");
    }
}

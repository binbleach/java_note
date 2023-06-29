package Class;

public class A {
    private String name = "名字：A";
    public A(){
        System.out.println("A...被创建了");
    }
    static {
        System.out.println("A...静态代码块被执行");
    }
    public String getName(){
        return name;
    }
}

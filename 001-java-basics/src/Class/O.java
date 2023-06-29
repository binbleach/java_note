package Class;

public class O {
    private String name = "名字：O";
    public O(){
        System.out.println("O...被创建了");
    }
    static {
        System.out.println("O...静态代码块被执行");
    }
    public String getName(){
        return name;
    }
}

package Class;
/*静态内部类创建单例对象*/
public class StaticInnerTest {

    public StaticInnerTest() {
        System.out.println("外部类无参构造函数");
    }

    public static StaticInnerTest getInstance() {
        return Inner.sit;
    }

    //方法不需要设置同步
    private static class Inner{
        private Inner(){
            System.out.println("静态内部类无参构造...");
        }
        private static final StaticInnerTest sit = new StaticInnerTest();
        public static StaticInnerTest tall(){
            System.out.println("内部类tall方法调用成功");
            return sit;
        }
        static {
            System.out.println("静态内部类的静态代码块执行了...");
        }
    }

    public StaticInnerTest say(){
        System.out.println("内部类say方法调用成功");
        return Inner.tall();
    }
}

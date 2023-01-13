package Override;

public class test {
    public static void main(String[] args) {
        Student mt = new Student(); //父类修饰符要低于子类，返回值要大于子类
        mt.eat();
    }
}

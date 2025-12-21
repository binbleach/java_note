package Class;

public class MyClass {
    static {
        System.out.println("MyClass is initializing...");
    }

    public static void staticMethod() {
        System.out.println("Static method called.");
    }

    public static void main(String[] args) {
        MyClass.staticMethod(); // 调用静态方法，不会触发类初始化（如果类还没有被初始化的话）
        // 但是，如果静态方法内部访问了类的静态字段或执行了其他需要类初始化的操作，那么会触发类初始化
    }
}

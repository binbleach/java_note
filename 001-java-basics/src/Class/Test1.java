package Class;

public class Test1 {
public static void main(String[] args) {
// 局部代码块
 {
    int n = 100;
}
// 局部代码块中声明的变量在代码块外部访问不到
// System.out.println(n); // 这行代码会报错
}
}

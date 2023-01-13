package Encapsulation;

public class Test {
    public static void main(String[] args) {
        Girl n1 = new Girl();
        //n1.age=18;  错误因为age 被private修饰封装了
        //Alt+ins快捷键创建get,set
        n1.setAge(18);
        n1.name="刘亦菲";
        System.out.println(n1.name+n1.getAge()+"岁");
        System.out.println(Test.fun(6));

    }
    static int fun(int n){
        if(n==1) return 0;
        if(n<=3) return 1;
        return fun(n-1)*fun(n-1)+fun(n-2)*fun(n-2);
    }
}

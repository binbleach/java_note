package 抽象类和接口;


/*
*   1、抽象里类不能被实例化，可以被继承
*   2、抽象类： 抽象方法 + 普通方法 + 随意属性 + is-a
*   3、接口：  抽象方法 + 默认方法 + 静态常量属性 + has-a
*   注：1、默认方法是由default修饰的，有方法体的，可以被重写的。2、抽象方法没有方法体的，必须被重写的。
*/
public class Test {
    public static void main(String[] args) {
        //Person a = new Person(); 抽象类不能被实例化
        //Star a = new Star();    错误同上
        Student me = new Student();
        me.take();
        me.pitu();
        me.getName();
        me.hobit();
        me.learn();
        System.out.println("人的母亲："+me.mother);  //调用抽象类属性
        System.out.println("照片大小："+me.size);    //调用接口属性
    }
}

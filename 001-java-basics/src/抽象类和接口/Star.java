package 抽象类和接口;

public abstract class Star extends Person {
    //抽象类继承抽象类不能重写抽象方法
    void learn(){
        System.out.println("我喜欢学习");
    }
}

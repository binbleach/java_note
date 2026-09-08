package 抽象类和接口;

public abstract class Person {  //抽象类
    public int mother = 1;
    protected abstract void hobit(); // 抽象方法
    abstract void learn();
    public void getName(){
        System.out.println("喂！！！");
    }
}

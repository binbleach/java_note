package base;

public class Gouzaoqi {
    public static void main(String[] args) {
        Persons matou = new Persons("matou",20,170);
        System.out.println(matou.age+matou.name+matou.height);
        matou.eat();

        Pet dahuang = new Pet("black",21,1);
        dahuang.play();
        dahuang.eat();

        Room min= new Room(); // 写了构造器默认的空的构造器就失效了（没用的知识，idea会提醒的）
        min.size = 16;
        System.out.println(min.size);
        min.sleep();

    }
}
class Room{
    int size;
    Room(){
    }
    Room(int size){
        this.size=size;
    }
    void sleep(){
        System.out.println("你可以睡觉在这里");
    }
}

class Persons{
    String name;
    int age;
    double height;

    public Persons(String name,int age,double height){  //三个参数的构造器
        this.name=name;
        this.age=age;
        this.height=height;
    }

    public void eat(){
        int age=10;
        System.out.println(age);
        System.out.println(this.age);   //使其不会发生就近原则，取的是调用对象的属性
        System.out.println("我喜欢干饭");
    }
}

class Pet{
    String eyes;
    int weight;
    int age;

    public Pet(){   //空构造器
    }

    public Pet(String eyes){
        this.eyes=eyes;
    }

    public Pet(String eyes,int weight){
        this(eyes);          //this修饰构造器,必须放在第一行
        this.weight=weight;
    }

    public Pet(String eyes,int weight, int age){
        this(eyes, weight);
        this.age=age;
    }

    public void play(){
        this.eat();
        System.out.println("玩");
    }

    public void eat(){
        System.out.println("吃");
    }
}

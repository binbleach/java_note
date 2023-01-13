package base;

public class gouzaoqi {
    public static void main(String[] args) {
        persons matou = new persons("matou",20,170);
        System.out.println(matou.age+matou.name+matou.height);
        matou.eat();

        Pet dahuang = new Pet("black",21,1);
        dahuang.Play();
        dahuang.eat();

        Room min= new Room(); // 没有写构造器的时候不能传递参数,不写就默认为空写了空就作废了
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

class persons{
    String name;
    int age;
    double height;

    public persons(String name,int age,double height){  //三个参数的构造器
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

    public void Play(){
        this.eat();
        System.out.println("玩");
    }

    public void eat(){
        System.out.println("吃");
    }
}

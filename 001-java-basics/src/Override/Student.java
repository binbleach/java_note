package Override;

public class Student extends Person {

    double score;
    public void eat (){
        System.out.println("去饭堂吃食物");
    }
    public Student(){
        super();
    }

    public Student (double score){
        this.score=score;
    }

    public Student(double score,String name,int age,double height){ // super用于构造器
        //this.score = score;
        //super.age = age;   // this.age = age ; 也可以
        //super.name = name;
        super(name,age,height); // this();调用构造器只能选一个
        super.height = height;
    }

    public String toString() {  //对toString重写
        return score + name + age + height;
    }

}

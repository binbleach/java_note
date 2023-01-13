package Override;

public class Person {
    String name;
    int age;
    double height;
    public void eat(){
        System.out.println("吃食物");
    }
    public Person(){

    }
    public Person(String name,int age,double height){
        this.name = name;
        this.age = age;
        this.height = height;
    }
}

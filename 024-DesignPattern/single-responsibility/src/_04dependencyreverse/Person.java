package _04dependencyreverse;

/*
    依赖倒置：代码要依赖于抽象的类，而不要依赖于具体的类。也就是面向接口编程
        person不直接依赖dog、cat，通过依赖dog、cat实现的animal接口，去调用他们的方法
*/
 interface Animal{
     void eat();
}
class Person{
    public void feed(Animal animal){
        animal.eat();
    }
}
class Dog implements Animal {
    public void eat() {
        System.out.println("狗吃狗粮");
    }
}
class Cat implements Animal{
    public void eat() {
        System.out.println("猫吃猫粮");
    }
}
class Main{
    public static void main(String[] args) {
        Person p = new Person();
        Dog d = new Dog();
        p.feed(d);
        Cat c = new Cat();
        p.feed(c);
    }
}

package Polymorphism;

public class Tes {
    public static void main(String[] args) {
        Girl g = new Girl();
        Cat c = new Cat();
        Dog d = new Dog();
        Animal a = d;   //转型 向上
        //a.eat(); 报错因为只看的到Animal的类型
        Dog p = (Dog)a; // 转型 向下
        p.eat(); // 完事可以调
        g.play(d);
        g.play(c);
    }
}

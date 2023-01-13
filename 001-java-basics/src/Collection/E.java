package Collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class E {
    static void E(){
        /*
        泛型的好处是可以迭代指定的泛型对象不用强制转换类型
        坏处是破坏了多样性，不过实际开发中实用
         */
        List <Animal> mylist = new ArrayList<Animal>(); //jdk8之后后面的Animal可以不写了
        Cat cat = new Cat();
        Dog dog = new Dog();

        mylist.add(cat);
        mylist.add(dog);
        mylist.add(new Animal());

        Iterator <Animal> e = mylist.iterator();
        while(e.hasNext()){
            Animal obj = e.next(); //泛型的好处就是你可以之迭代ANIMAL
            //不过也只能调用Animal里的方法
            obj.move();
        }

        String s = new String("ABCDEFGHI");
        System.out.println(s.substring(2));

    }
}

class Animal{
    void move(){
        System.out.println("动物移动");
    }
}
class Cat extends Animal{
    void aa(){

    }
}
class Dog extends Animal{

}
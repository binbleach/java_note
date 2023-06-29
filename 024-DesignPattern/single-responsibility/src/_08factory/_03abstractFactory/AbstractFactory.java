package _08factory._03abstractFactory;

/*
    抽象工厂
*/
public class AbstractFactory {
    public static void main(String[] args) {
        Factory ff = new KFCFactory();
        Food food = ff.getFood();
        food.eat();

        Factory colaFactory = new KFCFactory();
        Drink drink = colaFactory.getDrink();
        drink.drink();
    }
}
//食物和饮料接口
interface Food {
    void eat();
}
interface Drink{
    void drink();
}

//食物和饮料具体产品
class Hamburger implements Food {
    @Override
    public void eat() {
        System.out.println("汉堡包");
    }
}
class RiceNoodle implements Food {
    @Override
    public void eat() {
        System.out.println("过桥米线");
    }
}
class Cola implements Drink {
    @Override
    public void drink() {
        System.out.println("肥宅快乐水");
    }
}
class IcePeak implements Drink {

    @Override
    public void drink() {
        System.out.println("冰峰汽水");
    }
}

//工厂接口
interface Factory{
    Food getFood();
    Drink getDrink();
}
//kfc工厂实现类
class KFCFactory implements Factory {
    @Override
    public Food getFood() {
        return new Hamburger();
    }
    @Override
    public Drink getDrink() {
        return new Cola();
    }
}
//三秦工厂实现类
class SanQinFactory implements Factory {
    @Override
    public Food getFood() {
        return new RiceNoodle();
    }
    @Override
    public Drink getDrink() {
        return new IcePeak();
    }
}

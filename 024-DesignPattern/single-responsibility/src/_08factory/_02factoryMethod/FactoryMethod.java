package _08factory._02factoryMethod;

/*
    工厂方法
*/
class FactoryMethod {
    public static void main(String[] args) {
        FoodFactory ff = new HamburgerFactory();
        Food food = ff.getFood();
        food.eat();

        DrinkFactory colaFactory = new ColaFactory();
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
class Cola implements Drink{
    @Override
    public void drink() {
        System.out.println("肥宅快乐水");
    }
}
class IcePeak implements Drink{

    @Override
    public void drink() {
        System.out.println("冰峰汽水");
    }
}

//食物工厂接口
interface FoodFactory{
    Food getFood();
}
//食物工厂实现类
class HamburgerFactory implements FoodFactory{
    @Override
    public Food getFood() {
        return new Hamburger();
    }
}
class RiceNoodleFactory implements FoodFactory{
    @Override
    public Food getFood() {
        return new RiceNoodle();
    }
}
//饮料工厂接口
interface DrinkFactory{
    Drink getDrink();
}
//饮料工厂实现类
class ColaFactory implements DrinkFactory{
    @Override
    public Drink getDrink() {
        return new Cola();
    }
}
class IcePeakFactory implements DrinkFactory{
    @Override
    public Drink getDrink() {
        return new IcePeak();
    }
}

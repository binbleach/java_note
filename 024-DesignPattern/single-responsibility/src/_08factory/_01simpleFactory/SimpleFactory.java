package _08factory._01simpleFactory;

/*
    一、简单工厂：只有一个工厂类。符合 ‘依赖倒置原则’和 ’迪米特法则‘
        优点：
            1、把具体产品的创建从客户端代码中解耦出来，让客户端专注于‘消费’产品。服务端改了产品名客户端也不知道。
        缺点：
            1、客户端不得不死记硬背常量与产品的映射关系，不好记
            2、随着产品的增加工厂会变得特别臃肿，会有很多case
            3、若想拓展，添加新产品，则要修改源代码，违反了开闭原则。

    二、工厂方法：工厂接口数量为产品等级数量，工厂类数量为产品数量，一个工厂只生产一个产品。符合 ‘依赖倒置原则’和 ’迪米特法则‘和‘开闭原则’
        优点：
            1、具有简单工厂优点
            2、若想拓展添加新产品，不需要改源代码，只需要新加产品和工厂即可。符合开闭原则
        杠点：
            1、客户端还是有工厂名，服务端改了客户端也要改，回到原点。
                解释：对于工厂接口名。作者有义务保证它是稳定的（并不是100%）
            2、本来作者和使用者都是一个人，就直接改呗，这么麻烦干嘛。
                解释：通常作者在创建具体产品和抽象产品时，会配套创建对产品的使用的业务，这是一套框架，要保证他的可拓展性
        缺点：
            1、随着产品等级增多，工厂类会暴炸式增长
                注：食物和饮料是产品等级，KFC和SanQin是产品簇

    三、抽象工厂：工厂接口只有一个，工厂类数量为产品簇数量，一个工厂生产多个产品。符合 ‘依赖倒置原则’和 ‘迪米特法则’
        优点：
            1、有简单工厂和工厂方法的优点
            2、随着产品等级增多，工厂类不会增多
        缺点：
        1、增加产品等级会修改源代码，不符合开闭原则
*/
class SimpleFactory {
    public static void main(String[] args) {
        FoodFactory foodFactory = new FoodFactory();
        Food food = foodFactory.getFood(1);
        food.eat();
    }
}
interface Food {
    void eat();
}
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
class FoodFactory {
    public Food getFood(int n) {
        Food food = null;
        switch (n) {
            case 1:
                food = new Hamburger();
                break;
            case 2:
                food = new RiceNoodle();
                break;
        }
        return food;
    }
}

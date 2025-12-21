package _10builder;

public class Main {
    /*
        建造者模式
        需求：当创建一个对象要设置很多他的属性有很多过程，并不是所有属性我们都需要知道，违法了迪米特法则
        解决：我们把set抽离出去，不同类型的对象抽离出不同的实例化方法，此时我们无法保证每一个方法的属性
        解决：我们抽离出建造者MealBuilder，使得每个属性都是抽象方法必须去实现，此时我们无法保证调用顺序
        解决：我们创建一个指挥者，保证属性设置的顺序
    */
    public static void main(String[] args) {
        MealBuilder computerBuilder = new SubMealBuilderA();
        KFCWaiter kfcWaiter = new KFCWaiter(computerBuilder);
        MealBuilder computerBuilderB = new SubMealBuilderB();
        KFCWaiter kfcWaiterB = new KFCWaiter(computerBuilderB);
        System.out.println(kfcWaiter.construct());
        System.out.println(kfcWaiterB.construct());
    }
}
//指挥者类：KFCWaiter（服务员）
class KFCWaiter {
    private MealBuilder mb;

    public KFCWaiter(MealBuilder mb){
        this.mb = mb;
    }

    public Meal construct() {
        mb.buildFood();
        mb.buildDrink();
        return mb.getMeal();
    }
}
//建造者类：MealBuilder
abstract class MealBuilder
{
    protected Meal meal = new Meal();

    public abstract void buildFood();
    public abstract void buildDrink();
    public Meal getMeal(){
        return meal;
    }
}
//具体实现类
//SubMealBuilderA
class SubMealBuilderA extends MealBuilder {
    public void buildFood() {
        meal.setFood("鸡腿堡");
        System.out.println("鸡腿堡制作中...");
    }
    public void buildDrink() {
        meal.setDrink("可乐");
        System.out.println("一杯可乐制作中...");
    }
}
//SubMealBuilderB
class SubMealBuilderB extends MealBuilder {
    public void buildFood() {
        meal.setFood("鸡肉卷");
        System.out.println("鸡肉卷制作中...");
    }
    public void buildDrink() {
        meal.setDrink("果汁");
        System.out.println("果汁制作中...");
    }
}

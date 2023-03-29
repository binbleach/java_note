package _08factory.simpleFactory;

/*
    简单工厂，蕴含 ‘依赖倒置原则’和 ’迪米特法则‘
    优点：
        1、把具体产品的类型，从客户端代码中，解耦出来
        2、服务端改了具体类名，客户端也不知道。符合了面向接口编程的思想
    缺点：
        1、一个类对应一个名称，不好记
        2、若对工厂进行拓展，则要修改源代码，违反了开闭原则。
*/
class SimpleFactory {
    interface Food {
        void eat();
    }
    static class Hamburger implements Food {
        @Override
        public void eat() {
            System.out.println("包子");
        }
    }
    static class RiceNoodle implements Food {
        @Override
        public void eat() {
            System.out.println("馒头");
        }
    }
    static class FoodFactory {
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
    public static void main(String[] args) {
        FoodFactory foodFactory = new FoodFactory();
        Food food = foodFactory.getFood(1);
        food.eat();
    }
}

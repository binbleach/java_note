package _03interfaceIsolation.nagtive;

class Dog implements Animal {
        @Override
        public void eat() {
            System.out.println("狗啃");
        }
        @Override
        public void swim() {
            System.out.println("狗刨");
        }
        // 狗不会飞，不需要实现，违反接口隔离原则
        @Override
        public void fly() {
        System.out.println("你行，你来！！！");
    }
}

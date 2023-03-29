package _03interfaceIsolation.positive;

class Dog implements Swimable,Eatable{

    @Override
    public void eat() {
        System.out.println("狗啃");
    }

    @Override
    public void swim() {
        System.out.println("狗刨");
    }
}

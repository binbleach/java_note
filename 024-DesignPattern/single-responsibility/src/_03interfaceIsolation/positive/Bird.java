package _03interfaceIsolation.positive;

class Bird implements Flyable,Eatable{

    @Override
    public void eat() {
        System.out.println("口交");
    }

    @Override
    public void fly() {
        System.out.println("高潮");
    }
}

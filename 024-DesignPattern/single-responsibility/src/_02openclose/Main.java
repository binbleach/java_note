package _02openclose;

/*
    开闭原则：对修改原有功能是关闭的，对拓展新功能是开放的
*/
public class Main {
    public static void main(String[] args) {
        Car car = new DiscountCar();
        car.setBand("bm");
        car.setColor("red");
        car.setPrice(13.3f);
        System.out.println(car.toString());
    }
}

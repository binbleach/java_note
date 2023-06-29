package _02openclose;

/*
    开闭原则：对修改原有功能是关闭的，对拓展新功能是开放的
    例：如果想要每辆车打8折，可以继承Car，重写setPrice。符合开闭原则
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

package gongchan;

public class Test {
    public static void main(String[] args) {
        Animal an=Petstore.getanimal("猫");
        Cat a = (Cat)an;
        a.a();
        System.out.println(Petstore.getanimal("猫"));

    }
}

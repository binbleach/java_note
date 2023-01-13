package Equals;

public class Equals {
    public static void main(String[] args) {
        Phone s1 = new Phone("mix2s",3600,2019);
        Phone s2 = new Phone("mix2s",3600,2019);
        boolean r=s1.equals(s2);
        System.out.println(r);
        Cat a = new Cat();
        System.out.println(a.equals(s1)); //可以
        System.out.println(s1.equals(a));

    }
}

package map.SortedMap.TreeMap;

import java.util.Comparator;
import java.util.TreeSet;

public class comparator {
    public static void main(String[] args) {
        //利用比较器进行比较
        TreeSet<Customer2> a = new TreeSet(new Customer2comparator()); //注意这里不写会变成Object类型
        a.add(new Customer2(2));
        a.add(new Customer2(10));
        a.add(new Customer2(1));
        System.out.println(a);
        for (Customer2 o  : a) {
            System.out.println(o);
        }

    }
}
class Customer2{
    int age;

    public Customer2() {
    }

    public Customer2(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Customer2{" +
                "age=" + age +
                '}';
    }
}
class Customer2comparator implements Comparator<Customer2>{

    @Override
    public int compare(Customer2 o1, Customer2 o2) {
        return o1.age-o2.age;
    }
}

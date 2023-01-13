package map.SortedMap.TreeMap;

import java.util.*;

public class comparable {
    public static void main(String[] args) {
        /*想要TreeSet里的自定义数据类型进行比交得实现Comparable接口 重写compareTo()方法*/
        Set<Customer> map = new TreeSet<>(); //这里和TreeSet map = new TreeSet();一样
        map.add(new Customer(18));
        map.add(new Customer(20));
        map.add(new Customer(13));
        System.out.println(map);
    }
}
class Customer implements Comparable<Customer>{
    int age;

    public Customer() {
    }

    public Customer(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(Customer a) {
        return this.age-a.age;
    } //调个个就是降序

    @Override
    public String toString() {
        return "年龄"+this.age;
    }
}

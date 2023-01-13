package map;

import javax.swing.text.html.parser.Entity;
import java.util.*;

public class map {
    /*
    1、允许有空且同为空会被覆盖
    2、初始化16个数组到达百分之七十五之后扩容 是原容量的两倍 ，为了好点最好定的容量是2的倍数
    3、数组和单链表的形式组成的哈希表
    4、存的时候先用key取得hashCode值然后通过哈希算法算得数组下标，如果数组有元素就用equals方法比较key相同则覆盖value
    不同就加到链表下
    5、取的时候是用key的hashCode方法得到的数通过算法得到下标，下标为空就返回nall,不为空就用equals方法比较key有就取
    没有就返回空
     */
    public static void main(String[] args) {
        Map<Integer,String> mymap = new HashMap();
        //增加.put()方法
        mymap.put(1,"zhangsan");
        mymap.put(2,"lisi");
        mymap.put(3,"王五");
        mymap.put(4,"赵六");

        //.get();通过key找value
        System.out.println(mymap.get(1));
        System.out.println(mymap.get(2));

        //查看元素个数和是否为空
        System.out.println(mymap.size());
        System.out.println(mymap.isEmpty());

        //mymap.clear();     清除所有元素
        //mymap.remove(1);通过key删除元素

        //查看是否包含key和value
        System.out.println(mymap.containsKey(1));
        System.out.println(mymap.containsValue("zhangsan"));

        //map转set,静态内部类
        Set<Map.Entry<Integer,String>> set = mymap.entrySet(); //返回的是set不过是x=x的类型
        for (Map.Entry e :set) {
            /*System.out.println(e.getKey());*/
            /*System.out.println(e.getValue());*/
            System.out.print("我是Set<Map.Entry<Integer,String>>:"+e+",");
        }
        System.out.println("\n");

        Iterator<Map.Entry<Integer,String>> sets = set.iterator();
        while(sets.hasNext()){
            System.out.println(sets.next());
        }

        System.out.println(mymap.keySet());//取所有key 返回的是set
        System.out.println(mymap.values());//所有value 返回的是collection
    }
}

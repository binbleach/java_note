package map.HashMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class _HashMap {
    public static void main(String[] args) {
        /*HashMap
        * 底层是Hash表，由单链表和数组构成
        * 非线程安全的，key和value都可以为空
        * 1、允许有空且同为空会被覆盖
        * 2、初始化16个数组到达75%之后扩容 是原容量的两倍 ，为了好点最好定的容量是2的倍数
        * 3、数组和单链表的形式组成的哈希表
        * 4、存的时候先用key取得hashCode值然后通过哈希算法算得数组下标，如果数组有元素就用equals方法比较key相同则覆盖value
            不同就加到链表下
        * 5、取的时候是用key的hashCode方法得到的数通过算法得到下标，下标为空就返回null,不为空就用equals方法比较key有就取
            没有就返回空
        * 6、放在HashMap的key部分和HasHSet集合中的元素必须重写equals和hashCode
        * 7、1.8之前是数组加链表，1.8之后是红黑树
        * */
        HashMap hashMap = new HashMap();
        hashMap.put(1,"huangjiabin");
        hashMap.put(2,"徐凤年");
        hashMap.put(3,"徐龙象");
        Set set=hashMap.entrySet();
        Iterator i=set.iterator();
        while (i.hasNext()){
            System.out.println(i.next());
        }

    }
}

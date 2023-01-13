package Collection.List.LinkedList;

import java.util.LinkedList;
import java.util.List;

public class linkedlist {
    public static void main(String[] args){
        /*
        * 1、链表的优点，元素在空间存储内存上地址不连续，随机增删效率高。缺点：检索效率低，得从头节点开始找。
        * 2、非线程安全，没有初始化容量
        * 3、双链表：有上一个节点地址，数据，下一个节点地址
        * 4、面向接口编程，不需要关系哪个集合
        * */
        LinkedList list = new LinkedList();//这是双链表 特点是增删元素效率高，查找效率低
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add(1,"bbb");

        System.out.println(list.get(1));

        System.out.println(list);


    }
}

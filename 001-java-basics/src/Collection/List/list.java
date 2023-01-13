package Collection.List;
import java.util.*;

public class list {
    /*
    主要特色就是有下标，然后可重复
    底层是数组初始化是10扩容是原的1.5倍，优化是尽可能少的扩容，预算出数组的长度初始化长度
    >> 二进制右移符，>>1 右移一位减一半
    <<1左移一位
    数组特点：检索效率高，随机增删效率低，往数组末尾添加元素效率不受影响
     */
    public static void main(String args[]){
        System.out.println("list有自己特有的方法此时就不能再newCollection了而是new List");
        List list= new ArrayList();
        list.add("A");
        list.add(0,"B"); //list特有方法,根据下标添加元素，用的少
        list.add(4);
        list.add(0);
        list.add("C");
        list.add("C");
        System.out.println("现集合下标为0的元素:"+list.get(0)); //List特有方法，获取元素
        list.remove(0); // list特有方法，根据下标删除元素
        System.out.println("A的下标："+list.indexOf("A"));
        System.out.println("最后一个C元素的下标:"+list.lastIndexOf("C"));

        Iterator s = list.iterator();
        while(s.hasNext()){
            System.out.print(s.next());
        }
        System.out.println("\n");
        list.set(0,"B"); //List特有方法特有方法，根据下标设置元素
        list.set(0,"c");
        for (Object o : list) {
            System.out.print(o);
        }

    }
}

package Collection;

import java.util.*;

/*Collection接口的方法，则List接口(实现类有ArrayList,linkedlist,vector)Set接口(实现类有HashSet,TreeSet)
    都可以用。*/
public class _01Collection {
    public static void main(String[] args) {
        Collection c = new ArrayList();
        c.add(123);
        c.add("abc");
        c.add(456);

        c.remove(123);  //删除元素，如果时list的话有两种方式，一个元素名一个元素下标，底层调的也是重写了的equals方法
        System.out.println(c.size()); //查元素个数，返回int
        System.out.println(c.contains(123)); //是否包含返回 boolean 底层调的是equals方法，比较的是内容不是地址
        System.out.println(c.contains(456)); //重写了的比较的是内容没有重写比较的是地址，引用型重写了.对象放进集合得重写equals方法
        System.out.println(c.isEmpty()); //判断是否为空返会boolean
        c.clear(); //清空集合中的元素
        System.out.println(c.size());
        Object array[]=c.toArray();//将集合转化为数组
    }
}

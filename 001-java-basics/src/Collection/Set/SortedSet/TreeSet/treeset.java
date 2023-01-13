package Collection.Set.SortedSet.TreeSet;

import java.util.Set;
import java.util.TreeSet;

public class treeset {
    public static void main(String[] args) {
        /*
        特点是无序不可重复
        不过它可以给你排序
        想要TreeSet里的自定义数据类型进行比交得实现Comparable接口 重写compareTo()方法
         */
        Set strs = new TreeSet();
        strs.add("a");
        strs.add("A");
        strs.add("C");
        strs.add("B");
        for (Object str : strs) {
            System.out.println(str);
        }
    }
}

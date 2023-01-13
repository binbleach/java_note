package Collection.Set.HashSet;

import java.util.HashSet;
import java.util.Set;

public class hashset {
    public static void main(String[] args){
        /*
        无序不可重复也可以用增强for循环
        实际上放在HashMap的Key部分了
        放在HashMap的key部分和HasHSet集合中的元素必须重写equals和hashCode
         */
        Set a = new HashSet();
        a.add("hello");
        a.add("hello");
        a.add("hello");
        a.add("newew");
        for (Object o : a) {
            System.out.println(o);
        }

    }
}

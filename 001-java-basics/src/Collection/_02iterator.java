package Collection;
import java.util.*;
public class _02iterator {
    //迭代器是一个对象，集合只要发生改变迭代器必须重新获取
    public static void main(String args[]) {
        Collection a = new ArrayList();
        a.add(123);
        a.add("abc");
        a.add(new Object());
        Iterator b = a.iterator();

        while(b.hasNext()){  //下一步是否可走
            System.out.println(b.next());  //走下一步并返回next返回值类型必须是Object
        }

    }
}

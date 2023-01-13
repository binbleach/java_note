package Collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class  remove {
    static void remove(){
        Collection e = new ArrayList();
        e.add(123);
        e.add("abc");
        e.add(456);
        e.add(new Object());
        Iterator a = e.iterator();
            System.out.println(a.next());
            a.remove();  //迭代器删除，这里删除的是123
            System.out.println(e.size());

        while (a.hasNext()){
            System.out.println(a.next());
            a.remove();
            e.remove(a.next());
        }
        System.out.println("---------------------------");
        Iterator d = e.iterator();
        while (d.hasNext()){
            System.out.println(d.next());
        }
        System.out.println("-------------------------");

    }
}

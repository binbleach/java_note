package Collection.List.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class vector {
    static void vector(){
        // 特点线城安全线程同步，初始化10，扩容是双倍递增变20。默认加载因子是0.75底层是哈希表
        //扩容：原容量*2+1

        List list = new Vector();
        list.add("a");
        list.add("b");
        List mylist = new ArrayList();
        Collections.synchronizedList(mylist); // 把非线程安全的转成线城安全的
    }
}

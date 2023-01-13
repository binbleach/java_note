package Collection.List.ArrayList;

import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class _ArrayList {
    /*
    * 1、(底层先创建一个长度为0的数组当添加一个元素时，容量是10)
    * 2、每次增长原容量的1.5倍  >>1二进制位运算，除2
    * 3、底层是一个Object[]数组 数组优点检索效率高，缺点随机增删元素效率低，但是数组末尾添加元素效率高
    * 4、优化：建议给一个预估容量，减少扩容次数。
    * 5、非线程安全
    */
    public static void main(String[] args) {
        //ArrayList的三种构造方法。
        ArrayList arrayList = new ArrayList(); //默认初始化10
        ArrayList arrayList1 = new ArrayList(100);//初始化容量100


        Collection c = new HashSet();
        c.add(100);
        c.add(200);
        c.add(300);
        //将 HashSet集合转为 ArrayList ,也是 Set 和 List的转换
        ArrayList arrayList2 = new ArrayList(c);
        arrayList2.add(4,5);    //报错，越界
        for(int i=0;i<arrayList2.size();i++){
            System.out.println(arrayList2.get(i));
        }


    }
}

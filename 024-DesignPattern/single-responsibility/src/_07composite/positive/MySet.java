package _07composite.positive;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/*
      设计功能：统计对象添加过的元素数量
        1、用继承重写的方法解决是不够优秀的
            1）无法保证 重写的HashSet.add满足其他人的调用，
            2）因为addAll底层调用了add才实现统计功能，无法保证随着版本更新后addAll方法内部一定会调用add。
            3）无法保证随着版本更新HashSet会不会添加新的添加元素的方法。
        2、用组合关联的方法解决是比较优秀的，因为只用了 HashSet.add的添加元素的功能，这是可以保证的
*/
public class MySet {
    private Set set = new HashSet<>();
    private int count =0;
    public int getCount(){
            return count;
        }

    public boolean add(Object o) {
        count++;
        return set.add(o);
    }

    @Test
    public void testMain(){
        MySet mySet = new MySet();
        mySet.add(1);
        mySet.add(2);
        mySet.add(3);
        System.out.println(mySet.getCount());
    }
}

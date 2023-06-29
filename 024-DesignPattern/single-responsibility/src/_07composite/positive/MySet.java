package _07composite.positive;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/*
    组合优于继承：
        设计功能：记录添加元素的数量（包含被删除）
        1、用继承重写（我们会继承父类的所有添加元素的方法，这是不可控的）
            1）如果只重写add不重写addAll()它源码调的是add()也能实现统计功能。可是无法保证随着版本更新addAll()一直会调add()
            2）如果重写addAll()保证它调add()。可是我们无法保证重写的方法适用于所有人去用
            3）无法保证随着jdk的版本更新HashSet会不会添加新的添加元素的方法。因为我们继承了HashSet,继承了它所有方法，
                当别人用了新的添加元素的方法，可是没有记录到，功能就失效了。
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

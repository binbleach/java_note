package _07composite.nagtive;

import org.junit.Test;

import java.util.HashSet;

public class MySet extends HashSet {
    private int count =0;
    public int getCount(){
            return count;
        }

    @Override
    public boolean add(Object o) {
        count++;
        return super.add(o);
    }
    @Test
    public void testMain(){
        MySet mySet = new MySet();
        mySet.add(1);
        mySet.add(2);
        mySet.add(3);
        mySet.addAll(mySet);
        System.out.println(mySet.getCount());
    }
}


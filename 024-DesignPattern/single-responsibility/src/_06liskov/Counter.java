package _06liskov;
/*
    里氏替换原则：任何使用父类的地方都能完美的替换成子类
*/

class Counter  {
    public int add(int i,int j) {
        return i+j;
    }
}

class soonCounter extends Counter{
    //这样是不允许的（子类覆盖了父类的非抽象方法）
    @Override
    public int add(int i, int j) {
    return i-j;
}
}

class Main{
    public static void main(String[] args) {
        Counter c = new Counter();
        System.out.println(c.add(100,200));
        //结果300
        Counter c1 = new soonCounter();
        System.out.println(c1.add(100,200));
        //结果-100
    }
}

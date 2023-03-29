package _06liskov;

class Counter  {
        public int add(int i,int j) {
            return i+j;
        }
    }

class soonCounter extends Counter{
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

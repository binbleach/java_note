package String;
/*
* 引用数据类型
* 双引号括起来的不可变，在方法区的字符串常量池中，垃圾回收器是不会回收常量的
* */
public class test {
    public static void main(String[] args) {
        String  a = "abcd";
        String b  ="abcd"+"xy";
        /*
        在这里""双引号括起来的一定在方法区的字符串常量池里有；这上面两个方法实际上有三个对象在常量池中。
        */
        String c = a+b;
        String e = "abcdabcdxy";
        System.out.println(c==e); //false c新new了对象不在常量池中

        String g = new String("abd");
        //在这里new的String对象在堆中有然后c在栈中有"abd"在方法区中有，栈指向堆堆指向方法区

    }
}

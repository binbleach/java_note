package com.huangjiabin.yh.minifast.a04_reference;

/*
*   java传递是值传递，但引用类型的传递方式常被误解为“引用传递”，实际上传递的是对象引用的副本（即内存地址的拷贝），而非对象本身。
*   传参的话形参和实参是两个变量，指向同一个内存空间。
*   重要：一个变量指向一个内存地址。不会变量指向变量。也就是一个变量地址改变不会影响另一个变量。只有属性改变可能会影响（相同地址）
*/
public class Reference {
    public static void main(String[] args) {
        test1();
        System.out.println("==============");
        test2();
    }

    public static void test1(){
        User user = new User("1","20","user");
        User user1 = user;
        user1.name = "user1";
        System.out.println(user.name); //user1：证明相同地址的变量属性改变会相互影响
        User user2 = user;
        user = new User("1","20","user2");
        System.out.println(user2.name);//user1：证明一个变量地址改变不会影响另一个变量
    }

    //参数传递是深拷贝
    public static void test2(){
        User user = new User("1","20","user");
        changeReference(user);
        System.out.println(user.name);  //1：user：形参user地址改变了，影响不到实参user
        changeReference2(user);
        System.out.println(user.name);  //2：user1：形参user地址未改变（相同地址），可以影响实参user
    }

    public static void changeReference(User user){
        user = new User("1","20","user1");
        user.name = "user1";
    }
    public static void changeReference2(User user){
        user.name = "user1";
    }
}

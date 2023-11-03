package Class;

/*
    名词解释：
        成员变量：方法体外的变量。包含：
            1）实例变量：没有static修饰的变量。随着对象创建而创建，对象的一部分。单例模式下非线程安全，非单例线程安全。
            2）静态变量（类变量）：有static修饰的变量。位于方法区，只会在类加载时创建（类只会加载一次），为所有对象共享，非线程安全
        局部变量：方法体内的变量。位于栈中，方法结束回收，是线程安全的
        ☠ 静态方法里和静态代码块里的变量属于局部变量
        代码块：
            1）静态代码块：类加载时执行，只执行一次 2）实例代码块：创建对象时执行 3）局部代码块，调用方法执行
            4）同步代码块：Synchronized关键词修饰的代码块。
    类实例化（创建对象）的时机：
        1）使用new关键字创建对象
        2）使用Class类的newInstance方法(反射机制)：Student student = Student.class.newInstance();
        3）使用Constructor类的newInstance方法(反射机制)
            //默认空参构造器构造
            Student student = Student.class.getConstructor().newInstance();
            //指定构造器构造
            Student student1=Student.class.getConstructor(int.class).newInstance(1);
        4）使用Clone方法创建对象
            //需要重写 Cloneable.clone()方法，内容：return (Student) super.clone();
            Student student = new Student();
            Student clone = student.clone();
        5）使用(反)序列化机制创建对象
            Student student = (Student) new ObjectInputStream(new FileInputStream("file.txt")).readObject();
    类实例化（创建对象）过程：
        1、判断类是否加载，没加载的去加载类
        2、在堆区分配对象需要的内存。分配的内存包括本类和父类的所有实例变量，但不包括任何静态变量
        3、对所有实例变量赋默认值。将方法区内对实例变量的定义拷贝一份到堆区，然后赋默认值
        4、执行实例初始化代码。初始化顺序是先初始化父类再初始化子类，初始化时先执行实例代码块然后是构造方法
    类初始化时机（不是创建对象，不调用构造函数）：
        1、创建类的实例，new的时候 2、访问类的静态变量 3、访问类的静态方法 4、反射 Class.forName
        5、初始化类的子类（会先初始化父类） 6、虚拟机启动的时候，定义main方法的那个类会加载
    不会触发类初始化：
        1、如果访问的是 static final int y = "y" (静态变量是基本类型或String的常量)。不会触发类初始化
            1）网上有人说是因为 y 在编译期间直接创建到常量池里。
            2）也有人说其实编译期间只是初始化字段的constantValue: int 1属性值。编译只生成class文件，不分配内存。
               JVM规范中：对static修饰的且含有constantValue属性的字段在准备阶段赋初值，初始化阶段赋值。
               对’静态变量是基本类型或String的常量‘赋值也是在初始化。
               但是HotSpot VM直接在准备阶段就对’静态变量是基本类型或String的常量‘赋值了。所以不触发初始化。
        2）通过子类访问父类的静态变量不会触发类初始化，因为静态变量定义在父类中
        3）通过数组定义来引用类，不会触发此类的初始化：Student [] stu = new Student[10]
     类加载过程：
        1、加载：读取class文件（各种来源），将其转化为某种静态数据结构存储在方法区内，并在堆中生成一个便于用户调用的
                java.lang.Class类型的对象。ref.getClass()或 ClassName.class 可获得
            +++
                jdk1.7前 元信息、字符串常量池、运行时常量池、静态变量都在方法区中，使用存储方式是永久代，存储在jvm中。
                jdk1.7后 字符串常量池存储在堆中
                jdk1.8后 静态变量存储在堆中。方法区存储方式改为元空间，存储在本地中。
            +++ 为什么要改变存储方式。
                永久代方式去存储类的元数据信息，我们不清楚一个程序到底有多少类需要被加载，且方法区位于JVM内存，
                我们不清楚需要给方法区分配多大内存，太小容易PermGen OOM(内存溢出)，太大，在触发Full GC时又极其影响性能，
                同时还存在一些内存泄露的问题
        2、连接：
            1）验证：验证是否符合jvm规范，保证不影响jvm的安全。各种验证分散在各个阶段，如在加载阶段对class文件格式验证。
            2）准备：给静态变量分配内存并赋值，基础类型赋0值，引用类型赋null值，基本类型的常量赋给定值。
            3）解析：将符号引用替换成直接引用。编译后的class文件中引用是一串字符串，解析阶段会去加载引用的类，替换成实际地址。
                    如果引用的是接口，是多态，则在运行时再解析。
        3、初始化（先父后子）：
            1）给类变量赋值
            2）执行静态代码块
            3）不加载静态内部类
            注：类加载不一定会类初始化，我们一般说类加载都是包含类初始化了的，没初始化就是没加载。
*/
public class Test {
    public static void main(String[] args) {
//        test2();
        test3();
    }
    /*
        搞懂类加载和类实例化
    */
    static public void test2(){
//        String n = OO.n;    // 调用 static String n = "n"       会触发OO类加载
//        String y = OO.y;    // 调用 static final String y = "y" 不会触发OO类加载
//        O o = OO.o;         // 调用 static O o = new 0()        会触发OO类加载，且O类加载和实例化
//        O oo = OO.oo;       // 调用 static final O oo = new O() 会触发OO类加载，且O类加载和实例化
//        O ooo = OO.ooo;     // 调用 static O ooo;               会触发OO类加载
//        O ooo = OO.oooo;    // 调用 static final O oooo = null; 会触发OO类加载
//        new OO(); // new OO 会触发类加载和实例化。且实例变量"AA a = new AA();"会触发 A加载和实例化，"AA aa;"则不会
        //注：对象创建 = 类加载+类实例化
    }

    static public void test3(){
        // a == b为 false，直接 new外部类的方式并不单例。但是外部类的创建使用并不会触发静态内部类的加载
        StaticInnerTest a = new StaticInnerTest();
//        StaticInnerTest b = new StaticInnerTest();
//        System.out.println(a == b);   //false
        // 我们将静态类的构造函数私有化了，使得它无法通过new得到
//        StaticInnerTest.Inner inner1 = new StaticInnerTest.Inner();
//        StaticInnerTest.Inner inner = new StaticInnerTest.Inner();
        //通过静态变量只加载初始化一次的属性构造出来的对象是单例的
//        StaticInnerTest s1 = StaticInnerTest.getInstance();
//        StaticInnerTest s2 = StaticInnerTest.getInstance();
//        System.out.println(s1==s2); //true
        /*测试ygsp的通过静态内部类的静态方法去获取静态内部类的静态属性，从而获取外部类对象的方法是否成功，结论是成功的，
        我们无需实例化静态内部类去调用*/
//        StaticInnerTest s3 = s1.say();
//        s3.say();
//        System.out.println(s1 == s3);

    }
}

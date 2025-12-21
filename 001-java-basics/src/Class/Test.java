package Class;

/*
    一、名词解释：
        成员变量：方法体外的变量。包含：
            1）实例变量：类实例化时创建。存在于堆中，共享则不安全（单例模式），不共享则安全。
            2）静态变量（类变量）：类加载时创建。位于方法区中（jdk1.8后位于堆中），为所有对象共享，非线程安全
        局部变量：方法体内的变量。方法执行时创建，方法结束回收。位于栈中，一定是线程安全的
        ☠ 静态方法里和静态代码块里的变量属于局部变量
        代码块：
            1）静态代码块：类初始化时执行，只执行一次
            2）实例代码块：创建对象时执行
            3）局部代码块，调用方法执行
            4）同步代码块：Synchronized关键词修饰的代码块。
    二、执行顺序：
        1、初始化main方法所在类和父类（先初始化父类再初始化子类）
        2、执行main方法
        3、遇到new对象，实例化类和父类（先实例化父类再实例化子类，如果类没初始化会先初始化）
    二、类加载：
            操作                          类加载     初始化    实例化
        1、创建对象（new/newInstance()）      会        会       会
        2、子类实例化                        会        会       会        注：如果是初始化就只初始化
        3、访问静态变量/方法                  会        会       不会      注：静态常量不会初始化
        4、使用反射（Class.forName()）       会        会       不会       注：Class.forName(false)不会初始化，默认为true
        5、虚拟机启动主类（包含main方法的类）   会        会       不会
        ——————————————————————————————————————————————————————————特殊：
        7、通过子类访问父类的静态变量           会       不会      不会      注：父类会初始化
        8、MyClass.class                   会       不会      不会
        9、定义对象数组（new MyClass[10]）    会       不会      不会
        10、ClassLoader.loadClass()        会       不会      不会
     三、类的生命周期：
        1、加载：1）将类的二进制数据（.class文件）读入内存中。
                2）将字节流所代表的静态存储结构转换为方法区的运行时数据结构。
                3）在堆中生成Class对象作为数据访问入口
            +++
                jdk1.6 类信息、常量池、静态变量、类加载器的引用、JIT编译后的代码等都存储在方法区中，
                    使用存储方式是永久代，存储在jvm中。
                jdk1.7 字符串常量池存储在堆中。
                jdk1.8 静态变量存储在堆中。方法区存储方式改为元空间，存储在本地中。
            +++ 为什么用元空间：解决了永久代面临的空间限制、低效的垃圾回收、以及复杂的内存管理等问题
                1、不受JVM堆内存限制，可以动态调节大小（永久代设置-XX:PermSize，元空间设置-XX:MetaspaceSize）
                2、不再和堆内存共享同一个垃圾回收机制，由JVM根据需要自动管理，减少了垃圾回收的复杂性和暂停时间。
        2、连接：
            1）验证：验证是否符合jvm规范，保证不影响jvm的安全。各种验证分散在各个阶段，如在加载阶段对class文件格式验证。
            2）准备：给静态变量分配内存并赋值，基础类型赋0值，引用类型赋null值，基本类型的常量赋给定值。
            3）解析：将符号引用替换成直接引用。编译后的class文件中引用是一串字符串，解析阶段会去加载引用的类，替换成实际地址。
                    如果引用的是接口，是多态，则在运行时再解析。
        3、初始化（先父后子）：
            1）给类静态变量赋值。2）执行静态代码块。3）不加载静态内部类。
        4、类卸载：
            由JVM自带的类加载器加载的类无法被卸载。际开发中，类卸载的实现难度较高，通常用于热部署等特定场景
*/
public class Test {
    public static void main(String[] args) {
        // 1、子调用父静态变量，子类加载不初始化，父类加载并初始化
        String motorcycle = Child.motorcycle;
//        test2();
//        test3();
    }
    /*
        搞懂类加载和类实例化
    */
    static public void test2(){
        String n = OO.n;    // 调用 static String n = "n"       会触发OO类加载
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

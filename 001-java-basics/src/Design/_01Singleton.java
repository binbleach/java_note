package Design;
/*
    单例模式
    1、单例类只能有一个实例。
    2、单例类必须自己创建自己的唯一实例。
    3、单例类必须给所有其他对象提供这一实例。
    使用场景：
    需要频繁创建的一些类，使用单例可以降低系统的内存压力，减少 GC。
    某类只要求生成一个对象的时候，如一个班中的班长、每个人的身份证号等。
    某些类创建实例时占用资源较多，或实例化耗时较长，且经常使用。
*/
//懒汉式，线程不安全
public class _01Singleton {
    private  static  _01Singleton INSTANCE; //这里不能加final，因为得到下面才初始化,加了final就必须在这里初始化
    private _01Singleton(){}
    public  static _01Singleton getInstance(){
        if(INSTANCE==null){     //如果两个线程同时进到if下然后会创建两个对象所以线程不安全
            INSTANCE=new _01Singleton();
        }
        return INSTANCE;
    }

    public static void main(String[] args) {    //测试是否线程安全
        for(int j=0;j<10;j++) {
            new Thread(() -> {          //java8新特新，lombok表达式 接口名(参数列表)->{} 如果默认知道这地方用哪个接口则可以省略，如果只有一条语句‘{}’也可以省略
                System.out.println(_01Singleton.getInstance().hashCode());
            }).start();
        }
    }



}

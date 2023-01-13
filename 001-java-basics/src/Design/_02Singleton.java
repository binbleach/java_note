package Design;
/*
    饿汉式，类一加载对象就被创建，线程安全
    jvm帮我们做线程安全的，因为类只会被加载一次
*/
public class _02Singleton {
    private static final  _02Singleton INSTANCE = new _02Singleton();
    private _02Singleton(){}
    public static  _02Singleton getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) {
        for(int i=0;i<100;i++){
            new Thread(()->{
                _02Singleton.getInstance();
            }).start();
        }
    }

}

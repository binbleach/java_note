package 抽象类和接口;

public interface Photo {
    public final static int size = 1080;
    public abstract void take();

    public default void pitu(){  //default必须要写
        System.out.println("默认美颜");
    }
}




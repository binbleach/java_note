package Interface;

public interface photo {
    public final static int a = 10;
    public abstract void take();
    
    default void pitu(){  //default必须要写
        System.out.println("我是非抽象方法");
    }
    }




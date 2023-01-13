package Reflex;

public class _15SuperCalssAndInterfaceClass {
    /*获取符类和接口*/
    public static void main(String[] args) throws ClassNotFoundException {
        Class stringclass =Class.forName("java.lang.String");

        Class superclass= stringclass.getSuperclass(); //获取符类
        System.out.println(superclass.getName());

        Class[] interfacea =stringclass.getInterfaces(); //获取接口
        for (Class aClass : interfacea) {
            System.out.println(aClass.getName());
        }
    }
}

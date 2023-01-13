package Abstract;

import java.lang.annotation.AnnotationFormatError;

public class test {
    public static void main(String[] args) {
        //Person a = new Person(); 抽象类不能被实例化
        //star a = new star();    错误同上
        Student a = new Student();//可以
    }
}

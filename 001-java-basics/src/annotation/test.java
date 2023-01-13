package annotation;
/*
* 数组是一个大括号只有一个元素的话可以不用
* */

public class test {
    int age;
    String name;
   /* @MyAnnotation() //注解中有属性必须给属性赋值*/
    @MyAnnotation(name="黄家宾",age = 18,value = "ada")  //这样就给属性赋值了
    public void a (){
    }
}

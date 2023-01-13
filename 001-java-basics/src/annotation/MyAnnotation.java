package annotation;

public @interface MyAnnotation {
    String name(); //注解的属性 如果注解有属性必须给属性赋值
    int age()default 18; //给属性设置默认值
    String value(); //属性是value可以不写 有两个不能省略
}

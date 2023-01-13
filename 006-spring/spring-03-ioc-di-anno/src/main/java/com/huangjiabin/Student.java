package com.huangjiabin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/*标准名
@Component(value = "MyStudent")
*/

/*常用*/
@Component("MyStudent")

/*第三种,不指定名的默认的，为类名的首字母小写名,如：student
@Component
*/
public class Student {
    /*标准类型注入*/

        /*01放在属性上*/
        /* 标准：@Value(value="黄家宾") */
        @Value("黄家宾")
        private String name;

        private Integer age;
        /*02放在set方法上，调的是set方法
        */
        @Value("23")
        public void setAge(Integer age) {
            this.age = age;
        }

    /*引用类型注入*/

        /*01默认值是byType*/
        @Autowired
        private School shcool;

        /*02设置值byName*/
        @Autowired
        @Qualifier("teacher") //标准写法@Qualifier(value="teacher") 里面是对象的id
        /*@Autowired(required = true)  required 默认就是true，表示注入失败报错。为false失败的话继续，值为null*/
        private Teacher teacher;

        /*
        * 03 @Resource注入 ,默认是通过name注入，如果name错误就会通过type注入
        * 如果只要byname的话：@Resource(name = "id"),在这样只通过名字不通过类型
        */


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", shcool=" + shcool +
                ", teacher=" + teacher +
                '}';
    }

    /*
* 除了@Component,Spring 还提供了 3 个创建对象的注解：
➢ @Repository 用于对 DAO 实现类进行注解
➢ @Service 用于对 Service 实现类进行注解
➢ @Controller 用于对 Controller 实现类进行注解
*/
}

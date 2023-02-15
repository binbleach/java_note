package com.huangjiabin.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*配置文件读取方式二：
    通过@Value注解读取
*/
@Component
public class User {
    @Value("${user1.name}")
    private String name;
    @Value("${user1.age}")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

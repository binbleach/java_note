package com.huangjiabin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*配置文件获得注入*/
@Component("subject")
public class Subject {
    @Value("${one}")
    private String one;
    @Value("${two}")
    private String two;

    @Override
    public String toString() {
        return "Subject{" +
                "one='" + one + '\'' +
                ", two='" + two + '\'' +
                '}';
    }
}

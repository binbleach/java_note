package com.huangjiabin.springboot;

import com.huangjiabin.springboot.model.TStudent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TStudentARTest {

    //tstudent.insert() 返回的是boolean类型
    @Test
    public void testArInsert(){
        TStudent tstudent = new TStudent();
        tstudent.setName("徐年");
        tstudent.setAge(20);
        boolean flag=tstudent.insert();
        System.out.println(flag);
    }
    //tstudent.updateById() 同样null的属性值不做更新处理
    @Test
    public void testArUpdateById(){
        TStudent tstudent = new TStudent();
        tstudent.setId(1);
        tstudent.setName("徐年");
        tstudent.setAge(18);
        boolean flag=tstudent.updateById();
        System.out.println(flag);
    }
    //tstudent.deleteById(5) 有两个方法一个带参数一个不带参数，不带参数则更具属性id删除。
    //这个删除结果true是大于或等于零，所以即使没有从数据库中删除数据也是true
    //没传值，也没设置属性id值，会报错
    @Test
    public void testArDeleteById(){
        TStudent tstudent = new TStudent();
        tstudent.setId(1);
        tstudent.setName("徐年");
        tstudent.setAge(18);
        boolean flag=tstudent.deleteById();
        System.out.println(flag);
    }
    //主要是看方法，再看属性
    @Test
    public void testArSelectById(){
        TStudent tstudent = new TStudent();
        tstudent.setId(1);
        tstudent.setName("徐年");
        tstudent.setAge(18);
        TStudent student=tstudent.selectById(2);
        List<TStudent> student1 = tstudent.selectAll(); //查询所有，与属性条件无关
        if(student!=null){
            System.out.println("学生是==========="+student);
        }
        student1.forEach(s->{
            System.out.println("all学生是==========="+s);
        });
    }

}

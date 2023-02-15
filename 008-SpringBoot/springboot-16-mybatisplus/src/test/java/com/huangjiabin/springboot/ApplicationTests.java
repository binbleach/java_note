package com.huangjiabin.springboot;

import com.huangjiabin.springboot.mapper.StudentMapper;
import com.huangjiabin.springboot.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
@RunWith(SpringJUnit4ClassRunner.class)
其中SpringRunner extends SpringJUnit4ClassRunner
让测试运行于Spring测试环境，以便在测试开始的时候自动创建Spring的应用上下文。
就可以直接使用spring容器，直接使用@Test注解，不用启动项目的spring容器。
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Autowired
    private StudentMapper studentMapper;

    //插入数据
    @Test
    public void testAdd(){

    }

    //deleteById：按主键删除
    @Test
    public void testDeleteById(){
        int rows=studentMapper.deleteById("a");
        System.out.println("deleteById"+rows);
    }
    //deleteByMap：按条件删除
    //多个条件and连接
    @Test
    public void testDeleteByMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("name",2); //这里即使传的是数字，也删除成功了
        map.put("phone",2);
        int rows=studentMapper.deleteByMap(map);
        System.out.println("DeleteByMap："+rows);
    }
    //deleteBatchIds：批处理方式，使用多个主键值，删除数据
    @Test
    public void testDeleteBatchIds(){
        List<String> list = new ArrayList<>();
        list.add("57c7847efd374310a7b138c98ffdd39c");
        list.add("69d13af06442468ca38a8ee9413ed2fa");

        int rows = studentMapper.deleteBatchIds(list);
        System.out.println("DeleteBatchIds："+rows);

        //小技巧，使用lambda表达式，快速构建集合
        List<Integer> list1 = Stream.of(1,2,3,4,5).collect(Collectors.toList());
        for(Integer a:list1){
            System.out.println(a);
        }
    }

    //selectById：按主键查询
    @Test
    public void queryById(){
        Student student = studentMapper.selectById("acadba2c-2fc6-4c13-9cc1-9db75a986c1e");
        if(student!=null){
            System.out.println("我是学生================="+student);
        }else{
            System.out.println("查询失败");
        }
    }

    //selectBatchIds：实现批处理查询，根据多个主键值查询， 获取到List
    @Test
    public void a(){
        List<String> list= new ArrayList<>();
        list.add("acadba2c-2fc6-4c13-9cc1-9db75a986c1e");
        list.add("e420f514e8b643bbaf5128edb12211f2");
        List<Student> studentList=studentMapper.selectBatchIds(list);
        //lambda小技巧
        studentList.forEach(s->{
            System.out.println("我是学生========="+s);
        });
    }

    //selectByMap：多条件查询。key是字段名，value是查询值
    //多个条件and连接
    @Test
    public void testSelectByMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("id","acadba2c-2fc6-4c13-9cc1-9db75a986c1e");
        //只显示下面这条查询结果，因为key值相同，哦哦好像是map的问题
        map.put("id","e420f514e8b643bbaf5128edb12211f2");
        map.put("name",1);  //即使是integer也能查
        List<Student> list=studentMapper.selectByMap(map);
        list.forEach(s->{
            System.out.println("我是学生======="+s);
        });
    }
}

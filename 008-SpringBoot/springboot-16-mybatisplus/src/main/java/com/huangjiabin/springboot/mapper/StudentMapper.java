package com.huangjiabin.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huangjiabin.springboot.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
    自定义Mapper，就是Dao接口。不需要在写mapper.xml文件
    1、要继承BaseMapper接口
    2、指定实体类
    BaseMapper是MP框架中的对象，定义17个操作方法（CRUD）
*/
@Repository
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}
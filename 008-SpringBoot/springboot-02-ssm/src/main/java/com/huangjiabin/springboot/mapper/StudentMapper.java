package com.huangjiabin.springboot.mapper;

import com.huangjiabin.springboot.domain.Student;
import com.huangjiabin.springboot.domain.swagger.BStudent;

import org.apache.ibatis.annotations.Mapper;

@Mapper //mybatis的注解，用于声明dao。 扫描mapper接口到spring容器,可以不在这里加，在Application类上加，
//@Repository    //这个注解是spring的只是可以避免@Autowired爆红
public interface StudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    int insertBStudent(BStudent record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}

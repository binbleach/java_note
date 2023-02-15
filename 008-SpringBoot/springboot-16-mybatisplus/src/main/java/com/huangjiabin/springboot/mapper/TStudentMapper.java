package com.huangjiabin.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huangjiabin.springboot.model.TStudent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/*
     T_StudentMapper是不需要使用的，MP需要使用T_StudentMapper获取到数据库的表的信息。
     如果不定义T_StudentMapper，MP会报错，找不到表的定义信息
*/
@Repository
@Mapper
public interface TStudentMapper extends BaseMapper<TStudent> {
}

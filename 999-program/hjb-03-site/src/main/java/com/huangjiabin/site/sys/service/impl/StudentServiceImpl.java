package com.huangjiabin.site.sys.service.impl;

import com.huangjiabin.site.sys.model.Student;
import com.huangjiabin.site.sys.mapper.StudentMapper;
import com.huangjiabin.site.sys.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}

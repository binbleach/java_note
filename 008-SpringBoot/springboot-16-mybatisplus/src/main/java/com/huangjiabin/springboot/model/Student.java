package com.huangjiabin.springboot.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//不能加@Component注解，因为会注入到容器，即使controller那边不是注入的，是new的student,id也相同
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    /*
        指定主键的方式
        value:主键字段的名称，如果是id，可以不用写
        type：指定主键的类型，表示主键的值是如何生成的
        老版本3.0.5：
            0.none 没有主键
            1.auto 自动增长(mysql, sql server)
            2.input 手工输入
            3.id worker: 实体类用 Long id ， 表的列用 bigint ，int 类型大小不够
            4.id worker str 实体类使用 String id, 表的列使用 varchar 50
            5.uuid 实体类使用 String id， 列使用 varchar 50
       新版本3.4.2：
            AUTO(0),
            NONE(1),
            INPUT(2),
            ASSIGN_ID(3),
            ASSIGN_UUID(4),
    */
    @TableId(value="id",type = IdType.ASSIGN_UUID)
    private String id;
    private String name;
    private String sno;
    private String phone;
    private String dormitory;

}
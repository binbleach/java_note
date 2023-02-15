package com.huangjiabin.springboot.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/*
  1、使用AR，要求实体类需要继承MP中的Model
  2、Model中提供了对数据库的CRUD的操作
  3、TStudent对应的表名是t_student。实体驼峰命名，列明下划线分割
  注：新版本service中提供了对数据库的CRUD的操作
*/
@Data
public class TStudent extends Model<TStudent> {
    //id设置要在数据库中设置自增，才能用IdType.AUTO。
    //但是其实你设置了自增也就可以不传id,不过这里不设置@Table会报错,可能默认不设置是ASSIGN_ID(3)或worker
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //指定属性和列名的对应关系，value="列名"
    @TableField(value = "name")
    private String name;
    private Integer age;

}

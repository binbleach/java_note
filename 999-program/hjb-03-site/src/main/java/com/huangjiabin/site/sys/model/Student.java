package com.huangjiabin.site.sys.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_student")
@ApiModel(value="Student对象", description="")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学生的用户id")
    private Long studentId;

    @ApiModelProperty(value = "学生的辅导员的用户id")
    private Long teacherId;

    @ApiModelProperty(value = "学号")
    private Long sno;

    @ApiModelProperty(value = "年级单位年")
    private Integer grade;

    @ApiModelProperty(value = "部门")
    private Integer department;

    @ApiModelProperty(value = "宿舍")
    private String dormitory;


}

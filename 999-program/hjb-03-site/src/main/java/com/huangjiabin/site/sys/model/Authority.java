package com.huangjiabin.site.sys.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_authority")
@ApiModel(value="Authority对象", description="权限表")
public class Authority implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "权限名")
    private String name;

    @ApiModelProperty(value = "是否被禁用，0未被禁用，1被禁用")
    private Integer disabled;

    @ApiModelProperty(value = "是否删除，0未删除，1删除")
    private Integer delFlag;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "角色列表")
    @TableField(exist = false)
    private List<Role> roles;


}

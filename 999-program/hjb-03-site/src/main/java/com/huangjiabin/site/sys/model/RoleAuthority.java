package com.huangjiabin.site.sys.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色关联权限表
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_authority")
@ApiModel(value="RoleAuthority对象", description="角色关联权限表")
public class RoleAuthority implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "权限id")
    private Long permissionId;


}

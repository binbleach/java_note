package com.huangjiabin.site.sys.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@TableName("sys_user")
@ApiModel(value="User对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    //@JsonFormat(shape = JsonFormat.Shape.STRING)  不用这了
    private Long id;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "居住地址")
    private String address;

    @ApiModelProperty(value = "用户身份")
    private Integer title;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "是否被禁用，禁用不可登录")
    private Integer disabled;

    @ApiModelProperty(value = "信用评级1-10，10为最高")
    private Integer credit;

    @ApiModelProperty(value = "是否被删除，1为删除，0为未被删除")
    private Integer delFlag;

    @ApiModelProperty(value = "旧密码")
    private String oldPassword;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "头像")
    private String photo;

    @ApiModelProperty(value = "角色")
    @TableField(exist = false)
    private List<Role> roles;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(roles==null){
            return null;
        }else {
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
            return authorities;
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.huangjiabin.site.sys.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 登录信息表
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_login_log")
@ApiModel(value="LoginLog对象", description="登录信息表")
public class LoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "登录的ip地址")
    private String ip;

    @ApiModelProperty(value = "登录位置")
    private String loginAddress;

    @ApiModelProperty(value = "登录时间")
    private LocalDateTime loginTime;

    @ApiModelProperty(value = "退出时间")
    private LocalDateTime outTime;


}

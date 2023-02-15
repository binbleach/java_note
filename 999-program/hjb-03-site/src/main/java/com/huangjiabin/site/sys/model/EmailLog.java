package com.huangjiabin.site.sys.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * @since 2022-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_email_log")
@ApiModel(value="EmailLog对象", description="")
public class EmailLog extends Model<EmailLog> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息id")
    @TableId(value = "email_log_id", type = IdType.ASSIGN_ID)
    private Long emailLogId;

    @ApiModelProperty(value = "接收用户id")
    private Long userId;
    @ApiModelProperty(value = "预定消息id")
    private Long reserveId;

    @ApiModelProperty(value = "接收专题")
    private Integer status;

    @ApiModelProperty(value = "路由key")
    private String routingKey;

    @ApiModelProperty(value = "交换机")
    private String exchange;

    @ApiModelProperty(value = "重试次数")
    private Integer count;

    @ApiModelProperty(value = "尝试时间")
    private LocalDateTime tryTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更改时间")
    private LocalDateTime updateTime;


}

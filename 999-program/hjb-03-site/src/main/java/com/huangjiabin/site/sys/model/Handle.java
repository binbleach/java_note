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
 * 
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_handle")
@ApiModel(value="Handle对象", description="")
public class Handle implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "操作信息id")
    private Long id;

    @ApiModelProperty(value = "操作目标id")
    private Long targetId;

    @ApiModelProperty(value = "操作人id")
    private Long userId;

    @ApiModelProperty(value = "操作")
    private Integer handleResult;

    @ApiModelProperty(value = "是否邮件")
    private Integer isEmail;

    @ApiModelProperty(value = "邮件是否成功")
    private Integer isEmailSuccess;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作目标表名")
    private Integer handleTarget;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime handleTime;


}

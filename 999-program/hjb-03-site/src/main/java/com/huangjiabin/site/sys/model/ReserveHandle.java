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
 * 预定处理表
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_reserve_handle")
@ApiModel(value="ReserveHandle对象", description="预定处理表")
public class ReserveHandle implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "处理信息id")
    private Long id;

    @ApiModelProperty(value = "被处理预定信息的id")
    private Long reserveId;

    @ApiModelProperty(value = "处理人id")
    private Long handlerId;

    @ApiModelProperty(value = "处理的时间")
    private LocalDateTime time;

    @ApiModelProperty(value = "处理结果")
    private Integer handle;

    @ApiModelProperty(value = "是否发送推送邮件（0为不发送，1为发送）")
    private Integer isMail;

    @ApiModelProperty(value = "邮件发送是否成功（0为不成功，1为成功）")
    private Integer isMailSuccess;

    @ApiModelProperty(value = "备注信息")
    private String remark;


}

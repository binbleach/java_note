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
 * 预约表
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_reserve")
@ApiModel(value="Reserve对象", description="预约表")
public class Reserve implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "申请场所的id")
    private Long targetId;

    @ApiModelProperty(value = "申请人id")
    private Long userId;

    @ApiModelProperty(value = "申请时候的时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "申请活动开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束活动时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "预约状态，0为不成功，1为成功，2为申请中")
    private Integer reserveStatus;

    @ApiModelProperty(value = "预定目标 49场地 50资源")
    private Integer reserveTarget;

    @ApiModelProperty(value = "是否被删除 0未被删除 1被删除")
    private Integer isDelete;

    @ApiModelProperty(value = "预定原因")
    private String reserveReason;

    @ApiModelProperty(value = "是否被取消")
    private Integer isCancel;


}

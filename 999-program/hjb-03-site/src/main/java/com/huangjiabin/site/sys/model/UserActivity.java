package com.huangjiabin.site.sys.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 活动参与表
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_activity")
@ApiModel(value="UserActivity对象", description="活动参与表")
public class UserActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参加活动用户id")
    private Long userId;

    @ApiModelProperty(value = "参加的活动的id")
    private Long activityId;

    @ApiModelProperty(value = "是否退出")
    private Integer isOut;

    @ApiModelProperty(value = "参加时间")
    private LocalDateTime joinTime;

    @ApiModelProperty(value = "退出时间")
    private LocalDateTime outTime;

    @ApiModelProperty(value = "是否参加")
    private Integer isAttend;

}

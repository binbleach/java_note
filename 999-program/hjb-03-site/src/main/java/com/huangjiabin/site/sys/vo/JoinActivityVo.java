package com.huangjiabin.site.sys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="参加活动vo", description="参加活动传入参数")
@AllArgsConstructor
@NoArgsConstructor
public class JoinActivityVo {
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "活动id")
    private Long activityId;
}

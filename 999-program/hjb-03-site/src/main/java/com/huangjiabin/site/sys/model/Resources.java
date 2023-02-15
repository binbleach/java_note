package com.huangjiabin.site.sys.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_resources")
@ApiModel(value="Resources对象", description="资源表")
public class Resources implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "存放地址")
    private String address;

    @ApiModelProperty(value = "价值")
    private Integer value;

    @ApiModelProperty(value = "计量单位")
    private String unit;

    @ApiModelProperty(value = "数量")
    private Integer number;

    @ApiModelProperty(value = "购入日期")
    private LocalDate accountDate;

    @ApiModelProperty(value = "使用年限")
    private Integer useYear;

    @ApiModelProperty(value = "剩余数量")
    private Integer surplus;

    @ApiModelProperty(value = "损坏率（百分比）")
    private Integer damageRate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否被删除")
    private Integer isDelete;

    @ApiModelProperty(value = "资源照片")
    private String photo;

    @ApiModelProperty(value = "自动通过延迟时间")
    private Integer passTime;

}

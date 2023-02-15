package com.huangjiabin.site.sys.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 场地信息表
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_place")
@ApiModel(value="Place对象", description="场地信息表")
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "场所id")
    private Long id;

    @ApiModelProperty(value = "场所名称")
    private String name;

    @ApiModelProperty(value = "场所描述")
    private String remark;

    @ApiModelProperty(value = "场所卫生")
    private Integer hygiene;

    @ApiModelProperty(value = "是否被删除（0为未删除，1未删除）")
    private Integer delFlag;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "安全评级，1-10，10级最安全")
    private Integer security;

    @ApiModelProperty(value = "容纳人数（单位个）")
    private Integer capacity;

    @ApiModelProperty(value = "创建时间")
    private LocalDate createTime;

    @ApiModelProperty(value = "是否被禁用（0为否，1为是）")
    private Integer disable;

    @ApiModelProperty(value = "场地照片")
    private String photo;

    @ApiModelProperty(value = "自动通过时间")
    private Integer passTime;




}

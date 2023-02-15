package com.huangjiabin.site.sys.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

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
 * @since 2022-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_activity")
@ApiModel(value="ActivityB对象", description="")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long reserveId;

    private Long userId;

    private Long placeId;

    private String title;

    private String label;

    private String type;

    private String locale;

    private String content;

    private String img;

    private LocalDateTime createTime;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime updateTime;

    private Integer favourNum;

    private Integer joinNum;

    private Integer needNum;

    private Integer readNum;


    private Integer isStop;

    private Integer isDelete;

    private Integer isTop;

    private Integer isPublic;

    private Integer impose;



}

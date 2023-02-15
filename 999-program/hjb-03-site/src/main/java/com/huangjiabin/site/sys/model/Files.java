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
 * 文件表
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_files")
@ApiModel(value="Files对象", description="文件表")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件id")
    private Long id;

    @ApiModelProperty(value = "文件名")
    private String name;

    @ApiModelProperty(value = "文件的后缀")
    private String suffix;

    @ApiModelProperty(value = "文件的大小（单位MB）")
    private Integer size;

    @ApiModelProperty(value = "上传的时间")
    private LocalDateTime uploadTime;

    @ApiModelProperty(value = "上传的人id")
    private Long uploadBy;

    @ApiModelProperty(value = "文件类别")
    private Integer category;

    @ApiModelProperty(value = "预留")
    private Integer type;


}

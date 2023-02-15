package com.huangjiabin.site.sys.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 资源连接文件表
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_resources_files")
@ApiModel(value="ResourcesFiles对象", description="资源连接文件表")
public class ResourcesFiles implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源id")
    private Long resourcesId;

    @ApiModelProperty(value = "文件id")
    private Long filesId;


}

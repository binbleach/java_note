package com.huangjiabin.site.sys.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据字典数据表（码表）
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dict_data")
@ApiModel(value="DictData对象", description="数据字典数据表（码表）")
public class DictData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据主键")
    private Integer dataId;

    @ApiModelProperty(value = "数据名称")
    private String dataName;

    @ApiModelProperty(value = "数据序号")
    private Integer dataOrder;

    @ApiModelProperty(value = "数据组")
    private String dataGroup;


}

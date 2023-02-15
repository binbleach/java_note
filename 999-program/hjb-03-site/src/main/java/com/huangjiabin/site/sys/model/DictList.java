package com.huangjiabin.site.sys.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据类型表
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dict_list")
@ApiModel(value="DictList对象", description="数据类型表")
public class DictList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据类型主键")
    private String listId;

    @ApiModelProperty(value = "数据类型的名称")
    private String listName;

    @ApiModelProperty(value = "数据类型的数据类型")
    private String listType;

    @ApiModelProperty(value = "数据类型描述")
    private String listDescribe;

    @ApiModelProperty(value = "data列表")
    @TableField(exist = false)
    private List<DictData> datas;

}

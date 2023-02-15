package com.huangjiabin.springboot.domain.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data  //get,set,toString
/*
@NoArgsConstructor 无参构造
@Getter
@Setter
*/
@ApiModel(value = "Student", description = "学生对象")
public class BStudent {
    @ApiModelProperty(value = "用户ID", name = "id")
    private String id;
    @ApiModelProperty(value = "学生姓名", name = "name")
    private String name;
    @ApiModelProperty(value = "学号", name = "sno")
    private String sno;
    @ApiModelProperty(value = "电话", name = "phone")
    private String phone;
    @ApiModelProperty(value = "宿舍", name = "dormitory")
    private String dormitory;

}

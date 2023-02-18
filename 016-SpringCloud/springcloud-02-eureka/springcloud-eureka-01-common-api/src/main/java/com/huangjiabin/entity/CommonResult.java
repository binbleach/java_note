package com.huangjiabin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    public static<T> CommonResult Success(T data){
        return new CommonResult(ResultCodeEnum.SUCCESS,data);
    }

    public CommonResult (ResultCodeEnum resultCodeEnum,T data){
        this(resultCodeEnum.SUCCESS.getCode(),resultCodeEnum.SUCCESS.getMessage(),data);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

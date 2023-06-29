package com.huangjiabin.sentinel.myHandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.ResultCodeEnum;

public class CustomerBlockHandler {
    public static CommonResult handlerException(BlockException blockException){
        return new CommonResult(ResultCodeEnum.FAIL,"AAA");
    }
    public static CommonResult handlerException2(BlockException blockException){
        return new CommonResult(ResultCodeEnum.DATA_ERROR,"BBB");
    }
}

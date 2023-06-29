package com.huangjiabin.seata.controller;


import com.huangjiabin.seata.domain.CommonResult ;
import com.huangjiabin.seata.service.StorageService ;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class StorageController {

    @Autowired
    private StorageService storageService;

    /**
     * 扣减库存
     */
    @RequestMapping("/storage/decrease")
    public CommonResult decrease(Long productId, Integer count) {
        log.info("Seata全局事务id=================>{}", RootContext.getXID());
        storageService.decrease(productId, count);
        return new CommonResult(200,"扣减库存成功！");
    }
}

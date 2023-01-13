package com.huangjiabin.mimissm.service.impl;

import com.huangjiabin.mimissm.mapper.ProductTypeMapper;
import com.huangjiabin.mimissm.pojo.ProductType;
import com.huangjiabin.mimissm.pojo.ProductTypeExample;
import com.huangjiabin.mimissm.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeMapper productTypeMapper;

    //查询所有商品类别
    @Override
    public List<ProductType> getAll() {
        //因为变化比较少，将查询的商品类别放在全局监听器里，哪里都能用
        return productTypeMapper.selectByExample(new ProductTypeExample());

    }

}

package com.huangjiabin.mimissm.service;

import com.github.pagehelper.PageInfo;
import com.huangjiabin.mimissm.pojo.ProductInfo;
import com.huangjiabin.mimissm.pojo.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoService {
    //显示所有商品（不分页）
    List<ProductInfo> getAll();

    //显示分页商品
    PageInfo splitPage(int pageNum,int pageSize);

    //增加商品
    int save(ProductInfo info);

    //按主键id查询商品
    ProductInfo getById(int pid);

    //更新商品
    int update(ProductInfo info);

    //单个删除商品
    int delete(int pid);

    //批量删除商品
    int deleteBatch(String[] pids);

    //多条件分页查询
    PageInfo<ProductInfo> selectCondition(ProductInfoVo vo,int pageSize);
}

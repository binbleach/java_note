package com.huangjiabin.mimissm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangjiabin.mimissm.mapper.ProductInfoMapper;
import com.huangjiabin.mimissm.pojo.ProductInfo;
import com.huangjiabin.mimissm.pojo.ProductInfoExample;
import com.huangjiabin.mimissm.pojo.vo.ProductInfoVo;
import com.huangjiabin.mimissm.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {
        //查询商品信息，查询所有，无条件查找
        return productInfoMapper.selectByExample(new ProductInfoExample()) ;
    }

    /*
        实现分页功能 select * from product_info limit 起始记录数=(当前页-1)*每页条数,每页取几条数

    */
    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        //分页插件使用PageHelper工具类完成分页设置,PageHelper相当于拦截器，自动拼接sql中select语句
        PageHelper.startPage(pageNum,pageSize);
        //进行PageInfo的数据封装

        //进行有条件的查询操作，必须要创建ProductInfoExample
        ProductInfoExample example = new ProductInfoExample();
        //设置排序，降序
        example.setOrderByClause("p_id desc");
        //设置完排序后，取集合，切记切记，一定在去集合之前设置PageHelper
        List<ProductInfo> list = productInfoMapper.selectByExample(example);
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo getById(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] pids) {
        return productInfoMapper.deleteBatch(pids);
    }

    @Override
    public PageInfo<ProductInfo> selectCondition(ProductInfoVo vo, int pageSize) {
        //去除集合前先设置PageHelper
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        return new PageInfo<>(list);
    }

}

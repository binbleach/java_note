package com.huangjiabin.service.impl;

import com.huangjiabin.dao.GoodsDao;
import com.huangjiabin.dao.SaleDao;
import com.huangjiabin.entity.Goods;
import com.huangjiabin.entity.Sale;
import com.huangjiabin.exception.NotEnoughException;
import com.huangjiabin.service.AddGoodsService;

public class AddGoodsServiceImpl implements AddGoodsService {
    private GoodsDao goodsDao;
    private SaleDao saleDao;

    public void buyGoods(int gid,int nums){
        System.out.println("Service开始执行");
        Sale sale = new Sale(gid,nums);
        saleDao.insertSale(sale);  //一条语句

        Goods goods=goodsDao.selectGoods(gid); //第二条语句
        if(goods==null){
            throw new NullPointerException("该商品id不存在"+gid);
        }else if(goods.getAmount()<nums){
            //异常
            throw new NotEnoughException("商品库存不足");
        }

        goods = new Goods(gid,nums);
        goodsDao.updateGoods(goods);        //第三天语句

    }

    public void setGoodsDao(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    public void setSaleDao(SaleDao saleDao) {
        this.saleDao = saleDao;
    }
}

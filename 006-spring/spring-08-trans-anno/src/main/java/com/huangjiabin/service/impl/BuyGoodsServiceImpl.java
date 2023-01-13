package com.huangjiabin.service.impl;

import com.huangjiabin.dao.GoodsDao;
import com.huangjiabin.dao.SaleDao;
import com.huangjiabin.entity.Goods;
import com.huangjiabin.entity.Sale;
import com.huangjiabin.exception.NotEnoughException;
import com.huangjiabin.service.BuyGoodsService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class BuyGoodsServiceImpl implements BuyGoodsService {
    private GoodsDao goodsDao = null;
    private SaleDao saleDao= null;

    /*事物注解*/ /*默认就够用了*/
    @Transactional(
            /*传播属性默认，REQUIRED有就用没有就创一个事物 ，REQUIRES_NEW 有没有事物都要创建一个，SUPPORTS有没有都可以*/
            propagation = Propagation.REQUIRED,
            /*隔离级别，MySql 的默认为 REPEATABLE_READ ；Oracle。默认为 READ_COMMITTED。*/
            isolation = Isolation.DEFAULT,
            /*只读，默认就是false*/
            readOnly=false,
            /*指定需要回滚的异常类。类型为 Class[]*/
            rollbackFor = {
                    NullPointerException.class,NotEnoughException.class
            }

    )
    @Override
    public void buyGoods(int gid, int nums) {

        Sale sale = new Sale(gid,nums);
        int num=saleDao.insertSale(sale);  //第一条Dml语句
        System.out.println(num);

        Goods goods1=goodsDao.selectGoods(gid);
        if(goods1==null){
            throw new NullPointerException("编号为"+gid+"的商品不存在");
        }else if(goods1.getAmount()<nums) {
            throw new NotEnoughException("库存不足");
        }
        Goods goods = new Goods(gid,nums);
        goodsDao.updateGoods(goods);      //第二条Dml语句
    }

    public void setGoodsDao(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    public void setSaleDao(SaleDao saleDao) {
        this.saleDao = saleDao;
    }
}

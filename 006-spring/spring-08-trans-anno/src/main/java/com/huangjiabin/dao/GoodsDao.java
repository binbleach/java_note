package com.huangjiabin.dao;

import com.huangjiabin.entity.Goods;

public interface GoodsDao {
    int updateGoods(Goods goods);
    Goods selectGoods(int id);
}

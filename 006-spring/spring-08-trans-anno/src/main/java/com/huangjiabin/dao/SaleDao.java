package com.huangjiabin.dao;

import com.huangjiabin.entity.Sale;

import java.util.List;

public interface SaleDao {
    int insertSale(Sale sale);
    List<Sale> selectSales();
}

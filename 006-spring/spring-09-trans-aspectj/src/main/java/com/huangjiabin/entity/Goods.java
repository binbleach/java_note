package com.huangjiabin.entity;

public class Goods {
    private Integer id;
    private String name;
    private Integer amount;
    private float price;



    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public Goods(){

    }
    public Goods(Integer id, Integer amount) {
        this.id = id;
        this.amount = amount;
    }
}

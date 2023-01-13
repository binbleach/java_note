package com.huangjiabin.entity;

public class Goods {
    private Integer id;
    private String name;
    private Integer amount;
    private float price;

    public Goods() {
    }

    public Goods(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }
}

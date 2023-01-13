package com.huangjiabin.fastdfs.model;

import java.math.BigDecimal;

public class CreditorInfo {
    private Integer id;

    private String realName;

    private String idCard;

    private String address;

    private Integer sex;

    private String phone;

    private BigDecimal money;

    //一对一的设计，表示一个合同信息包含一个合同文件
    private CreditorFile creditorFile;

    public CreditorFile getCreditorFile() {
        return creditorFile;
    }

    public void setCreditorFile(CreditorFile creditorFile) {
        this.creditorFile = creditorFile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
package com.huangjiabin.entity;

public class Sale {
    private Integer id;
    private Integer gid;
    private Integer nums;

    public Sale() {
    }

    public Sale(int gid, int nums) {
        this.gid = gid;
        this.nums = nums;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

}

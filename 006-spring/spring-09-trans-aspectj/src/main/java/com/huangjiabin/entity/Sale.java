package com.huangjiabin.entity;

public class Sale {
    private Integer id;
    private Integer gid;
    private Integer nums;

    public Sale() {
    }

    public Sale(Integer gid, Integer nums) {
        this.gid = gid;
        this.nums = nums;
    }



    public void setId(Integer id) {
        this.id = id;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }
}

package com.huangjiabin.entity;

public class MyStudent {
    private int myId;
    private String myName;

    public int getMyId() {
        return myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    @Override
    public String toString() {
        return "MyStudent{" +
                "myId=" + myId +
                ", myName='" + myName + '\'' +
                '}';
    }
}

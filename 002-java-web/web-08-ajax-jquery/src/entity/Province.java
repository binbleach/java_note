package entity;

public class Province {
    public int id;
    public String name;
    public String jiancheng;
    public String shenghui;

    public Province(int id, String name, String jiancheng, String shenghui) {
        this.id = id;
        this.name = name;
        this.jiancheng = jiancheng;
        this.shenghui = shenghui;
    }

    public Province() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJiancheng() {
        return jiancheng;
    }

    public void setJiancheng(String jiancheng) {
        this.jiancheng = jiancheng;
    }

    public String getShenghui() {
        return shenghui;
    }

    public void setShenghui(String shenghui) {
        this.shenghui = shenghui;
    }
}

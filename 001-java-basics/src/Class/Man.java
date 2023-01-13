package Class;


public class Man extends Human {
    private Human human;

    public Man(String thinking) {
        super(thinking);
    }
    public Man() {
        super("");
    }

    public void setHuman(){
        this.human=new Human("明天吃啥子");
    }
}

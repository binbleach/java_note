package Class;

public class NotChange {
    private String name = "我是不可变类";
    public void say(){
        System.out.println("甭管了我不变");
    }

    public NotChange(String name) {
        this.name = name;
    }

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }
}

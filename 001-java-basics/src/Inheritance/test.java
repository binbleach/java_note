package Inheritance;

public class test {
    public static void main(String[] args){
        Student lihua = new Student();
        lihua.age=18;
        lihua.setSex("男");
        System.out.println(lihua.age+lihua.getSex());
        lihua.tedian();
    }
}

package 抽象类和接口;

public class Student extends Person implements Photo {

    @Override
    protected void hobit(){
        System.out.println("我的爱好是唱歌");
    }
    @Override
    void learn(){
        System.out.println("我喜欢学习");
    }

    @Override
    public void take() {
        System.out.println("毕业照+1");
    }

    @Override
    public /*default必须不能加*/ void pitu() {
        System.out.println("学生不p图，保留真实的青春");
    }
}

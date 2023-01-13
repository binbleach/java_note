package Interface;

public class computer implements photo {
    @Override
    public void take() {

    }
    public /*default必须不能加*/ void pitu(){
        System.out.println("我重写了非抽象方法");
    }
}

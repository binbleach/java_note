package one;
/*
* 这个是模板设计模式,父类定义了模板子类无需再考虑方法何时调用
* 降低开发难度，父辈设置了模板再继承就不用了
* */
public class Father extends aFather{
    @Override
    public void service(String method) {
        if(method.equals("get")) {
            get();
        }else{
            post();
        }
    }
    public void get(){
        System.out.println("get请求方式");
    }
    public void post(){
        System.out.println("post请求方式");
    }
}

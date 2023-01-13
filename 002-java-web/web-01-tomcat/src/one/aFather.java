package one;
/*
* 这就是适配器设计模式,抽象类重写了init方法,子类就会不用写了
* 降低负担，主要实现servlet
*
* */
abstract public class aFather implements Servlet {
    @Override
    public void init(){} //空实现
}

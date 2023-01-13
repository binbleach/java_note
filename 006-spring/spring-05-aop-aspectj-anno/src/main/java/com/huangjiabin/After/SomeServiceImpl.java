package com.huangjiabin.After;

public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome() {
        System.out.println("===========After主业务doSome===============");
        /*System.out.println(10/0);*/
    }
}

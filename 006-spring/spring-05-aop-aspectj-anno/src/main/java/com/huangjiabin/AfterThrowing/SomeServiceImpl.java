package com.huangjiabin.AfterThrowing;

public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome(String a, int b) {
        System.out.println("===========AfterThrowing主业务doSome===============");
        System.out.println(10/0);
    }
}

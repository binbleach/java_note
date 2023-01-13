package com.huangjiabin.AfterReturning;

public class SomeServiceImpl implements SomeService {
    @Override
    public Student doSome(String a, int b) {
        System.out.println("===========AfterReturning主业务doSome===============");
        return null;
    }
}

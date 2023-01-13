package com.huangjiabin.Around;

public class SomeServiceImpl implements SomeService {
    @Override
    public String doSome(String a, int b) {
        System.out.println("===========Around主业务doSome===============");
        return "abc";
    }
}

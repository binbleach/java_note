package com.huangjiabin.Before;

import com.huangjiabin.Before.SomeService;

public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome(String a, int b) {
        System.out.println("==========Before主业务的doSome===============");
    }
}

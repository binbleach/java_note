package com.huangjiabin.Annotation;

import org.springframework.stereotype.Service;

@Service("SomeServiceImpl6")
public class SomeServiceImpl implements SomeService {
    @PrivacyEncrypt
    @Override
    public void doSome(String a, int b) {
        System.out.println("==========Annotation主业务的doSome===============");
    }
}

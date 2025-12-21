package com.huangjiabin.Annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class PrivacyEncryptAspect {

    @Before("@annotation(PrivacyEncrypt)")
    public void encryptPrivacy(JoinPoint joinPoint) throws Throwable {
        Object key = null;
        String a = String.valueOf(key);
        boolean equals = a.equals(null);
        System.out.println("aaaaa"+"aaa".contains(null));
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < parameters.length; i++) {
            String paramName = parameters[i].getName();
            if ("username".equals(paramName) || "tel".equals(paramName)) {
//                    args[i] = EncryptionUtil.encrypt(String.valueOf(args[i]));
            }
        }
    }
}

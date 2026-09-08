package com.huangjiabin.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/*
	要让@annotation()表达式生效，自定义注解必须满足：
	@Target(ElementType.METHOD)：指定注解只能作用于方法（符合切入条件）；
	@Retention(RetentionPolicy.RUNTIME)：指定注解在运行时保留（Spring AOP需要
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrivacyEncrypt {
}

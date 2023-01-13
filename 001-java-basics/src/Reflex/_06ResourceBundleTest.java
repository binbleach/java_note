package Reflex;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class _06ResourceBundleTest {
    public static void main(String[] args){
        //java.util包提供的资源帮顶器，不写拓展名,只能绑定xx.properties的文件
        //流都不用，就用这个
        ResourceBundle a = ResourceBundle.getBundle("path");
        String b=a.getString("name");
        System.out.println(b);
    }}

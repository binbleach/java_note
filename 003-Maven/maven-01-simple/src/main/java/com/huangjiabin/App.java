package com.huangjiabin;

/**
 * Hello world!
 *  packge和install的区别：
 *      package是把项目打成jar/war到target下，install会在package的基础上，把jar/war安装到本地的maven仓库，供其他项目使用。
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}

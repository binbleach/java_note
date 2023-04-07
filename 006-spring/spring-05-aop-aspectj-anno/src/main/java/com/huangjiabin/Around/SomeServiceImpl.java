package com.huangjiabin.Around;

public class SomeServiceImpl implements SomeService {
    @Override
    public String doSome(String a, int b) throws Exception {
        System.out.println("===========Around主业务doSome===============");
        try{
            if(true)
                throw new NullPointerException();
        }catch (NullPointerException e){
            System.out.println("service=========="+e.toString());
        }
        int c = 1/0;


        return "abc";
    }
}

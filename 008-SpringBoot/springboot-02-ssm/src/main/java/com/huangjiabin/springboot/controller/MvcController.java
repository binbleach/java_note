package com.huangjiabin.springboot.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//@RestController 相当于给这里加了@Conteoller和下面所有方法加了@ResponseBody
@RestController
public class MvcController {

    @RequestMapping(value = "/springboot/say" ,method = RequestMethod.GET)
    public @ResponseBody String say(){
        return "Hello SpringBoot";
    }

    @GetMapping(value="/springboot/say1")   //相当于上面一句话，只接收get请求，还有@PostMapping(新增用post)
    public String say1(){
        return "Hello GerMapping";
    }

    //@RequestMapping(value="/delete",method = RequestMethod.Delete
    @DeleteMapping(value = "/springboot/delete")   //相当于上一句，该注解通常在删除数据使用(这是只能用delete请求访问)，还有PutMapping
    public Object delete(){
        return "delete success";
    }

    //RESTful风格，配合上面的注解使用
    @RequestMapping(value="/springboot/update/{id}/{name}")
    public Object update(@PathVariable("id") Integer id,
                         @PathVariable("name") String name){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        return map;
    }

    //这里和上面混淆了，所以和注解配合使用,@PutMapping和@RequestMapping两种请求方式区分了，
    @PutMapping(value="/springboot/update/{id}/{status}")
    public Object update1(@PathVariable("id") Integer id,
                         @PathVariable("status") String name){

        return id+name;
    }

    //或者调换地址位置区分当然请求的位置也会变
    @PutMapping(value="/springboot/{id}/update/{status}")
    public Object update2(@PathVariable("id") Integer id,
                          @PathVariable("status") String name){

        return id+name+2;
    }

}

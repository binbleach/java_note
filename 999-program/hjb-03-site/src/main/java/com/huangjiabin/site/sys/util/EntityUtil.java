package com.huangjiabin.site.sys.util;

import com.huangjiabin.site.sys.model.RespBean;
import com.huangjiabin.site.sys.model.User;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class EntityUtil {
    /*
        判断user对象是否包含空属性
    */
    public static RespBean checkEntityAllFieldsIsNull(User user) {
        if(user.getUsername()==null||user.getUsername()==""){
            return RespBean.error("username不能为空");
        }
        if(user.getPassword()==null||user.getPassword()==""){
            return RespBean.error("password不能为空");
        }
        if(user.getPhone()==null||user.getPhone()==""){
            return RespBean.error("phone不能为空");
        }
        if(user.getAddress()==null||user.getAddress()==""){
            return RespBean.error(user.getAddress()+"不能为空");
        }
        if(user.getNickname()==null||user.getNickname()==""){
            return RespBean.error("address不能为空");
        }
        if(user.getTitle()==0||!"28,29,30".contains(user.getTitle().toString())){
            return RespBean.error("title非法");
        }
        return RespBean.success("ok");
    }
    /**
     * map转对象
     */
    public static <T> T mapToBean(Map map, Class<T> beanClass) {
        T object = null;
        try {
            object = beanClass.newInstance();
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                if (map.containsKey(field.getName())) {
                    if(field.getType().getSimpleName().equals("Long")){
                        if(map.get(field.getName()) instanceof Integer){
                            field.set(object,((Integer)map.get(field.getName())).longValue());
                        }else if(map.get(field.getName()) instanceof String){
                            field.set(object,Long.valueOf((String)map.get(field.getName())).longValue());
                        }else {
                            field.set(object,map.get(field.getName()));
                        }
                    }else if(field.getType().getSimpleName().equals("LocalDateTime")){
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime ldt;
                        if(((String)map.get(field.getName())).lastIndexOf("00")==((String)map.get(field.getName())).length()-2){
                            map.put(field.getName(),((String)map.get(field.getName())).substring(0,((String)map.get(field.getName())).length()-1)+"1");
                            ldt = LocalDateTime.parse((String)map.get(field.getName()),df);
                        }else {
                            ldt = LocalDateTime.parse((String)map.get(field.getName()),df);
                        }
                        field.set(object,ldt);
                    }else if(field.getType().getSimpleName().equals("LocalDate")){
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate ldt = LocalDate.parse((String)map.get(field.getName()),df);
                        field.set(object,ldt);
                    }
                    else {
                        field.set(object, map.get(field.getName()));
                    }

                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }
}

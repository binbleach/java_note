package com.huangjiabin.site.sys.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private Object data;

    /*
        成功，返回message
    */
    public static RespBean success(String message){
        return new RespBean(200,message,null);
    }

    /*
       成功，返回data
   */
    public static<Object> RespBean success(Object data){
        return new RespBean(200,"",data);
    }

    /*
        成功，返回message，data
    */
    public static<Object> RespBean success(String message,Object data){
        return new RespBean(200,message,data);
    }
    /**
     * 失败，返回message
     */
    public static RespBean error(String message){
        return new RespBean(500,message,null);
    }

    /*
      失败，返回message,data
    */
    public static<Object> RespBean error(String message,Object data){
        return new RespBean(500,message,data);
    }


}

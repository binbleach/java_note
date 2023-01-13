package map.HashTalbe;

import map.map;

import java.util.Hashtable;
import java.util.Map;

public class hashtable {
    //线程安全的synchronized
    // 运行时异常，hashtable的key部分不能为空value也是不能为空
    //底层也是哈希表的数据结构
    //初始化容量是11，默认加载因子是0.75达到75%就扩容，原容量*2+1
    public static void main(String[] args) {
        Map map = new Hashtable();
        try{map.put(null,"zhangsan");}// 运行时异常，hashtable的key部分不能为空value也是不能为空
        catch(NullPointerException e){
            System.out.println("空指针异常");
        }
    }
}

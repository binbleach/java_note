package agent;

import factory.UsbAppleFactory;
import factory.UsbKingFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//代理类淘宝
public class TaoBao implements InvocationHandler {
    Object target = null;
    public TaoBao(Object target){
        this.target=target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //不改动源代码UsbKingFactory的情况下功能增强
        int price=(int)method.invoke(target,args);
        if(target instanceof UsbAppleFactory){
            System.out.println("买苹果发顺丰快递，我赚100块钱");
            price=price+100;
        }else {
            System.out.println("买其他的发普通快递，我赚30块钱");
            price=price+20;
        }
        System.out.println("全部都送淘金币");
        return price;
    }
}

package factory;

import com.huangjiabin.service.USB;

public class UsbKingFactory implements USB {
    @Override
    public int price(int i) {
        System.out.println("原价40");
        return 40;
    }
}

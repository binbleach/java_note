package factory;

import com.huangjiabin.service.USB;

public class UsbAppleFactory implements USB {
    @Override
    public int price(int i) {
        System.out.println("原价200");
        return 200;
    }
}

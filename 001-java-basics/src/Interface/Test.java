package Interface;

import java.io.ObjectOutputStream;
import java.util.HashSet;

public class Test {
    public static void main(String[] args) {
        phone mixs2s = new phone();
        mixs2s.take();
        System.out.println(mixs2s.num(2));
        mixs2s.pitu();

        computer lenovo = new computer();
        lenovo.pitu();
    }
}

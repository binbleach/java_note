package exception;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class throws1{

    void a() throws FileNotFoundException {  //往上抛
        FileInputStream a = new FileInputStream("D:\\文件\\第6章代码.txt");
    }

}

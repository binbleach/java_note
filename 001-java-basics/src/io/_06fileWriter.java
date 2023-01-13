package io;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class _06fileWriter {
    public static void main(String[] args) {
        FileWriter writer = null;
        try {
            writer = new FileWriter("FileWriter",true);
            writer.write("abcdefg");
            char[] chars = {'我','是','中','国','人'}; //注意char是字符类型''单引号只存一个字符
            writer.write(chars);
            writer.write('\n');
            writer.write(chars,2,3);


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

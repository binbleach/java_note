package io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class _05fileReader {
    public static void main(String[] args) {
        FileReader fil = null;
        try {
            fil = new FileReader("FileReader01.txt");
            char [] c = new char[10];
            int readCount = 0;
            while((readCount=fil.read(c))!=-1){
                System.out.print(new String(c,0,readCount));
            }
            //可以遍历
            /*fil.read(c); //因为装不完不咋地好
            for (char c1 : c) {
                System.out.print(c1);
            }*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(fil!=null){
                try {
                    fil.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

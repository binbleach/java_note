package io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class _07ReaderAndWriteCopy2 {
    /*
    和字节差不多区别在于关键是char[]
    * */
    public static void main(String[] args) {
        FileReader in = null;
        FileWriter out = null;
        try {
            in= new FileReader("copyA.txt");
            out = new FileWriter("copyB.txt");
            char [] cs = new char[10];
            int readCount = 0;
            while((readCount = in.read(cs))!=-1){
                out.write(cs,0,readCount);
                System.out.println(cs);
            }
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

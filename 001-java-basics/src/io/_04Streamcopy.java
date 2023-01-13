package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
拷贝文件是一边读一边写 ,关键在byte型数组 byte[];
万能得  FileInputStream 和 FielOutputStream

 */
public class _04Streamcopy {
    public static void main(String[] args) {
        FileInputStream filin = null;
        FileOutputStream filout = null;

        try {
            filin = new FileInputStream("copy.txt");
            filout = new FileOutputStream("copy2.txt");

            byte bytes[]= new byte[1204*1204];
            int readCount = 0;
            while((readCount = filin.read(bytes))!=-1){
                filout.write(bytes,0,readCount);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(filin !=null){
                try {
                    filin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(filout !=null){
                try {
                    filout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

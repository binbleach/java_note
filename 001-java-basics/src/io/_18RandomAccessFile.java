package io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
    RandomAccessFile随机存取文件流
        既可以读也可以写,继承的是 Object
        写会从开头位置对原有内容覆盖，可以设置ref2.seek(0)控制覆盖位置

*/
public class _18RandomAccessFile {
    public static void main(String[] args) throws FileNotFoundException {
        //r表示只读，rw读写，rwd读写同步文件内容的更新，rws读写元数据更新
        RandomAccessFile ref1 = new RandomAccessFile("001-java-basics/resources/小埋.jpg","r");
        RandomAccessFile ref2 = new RandomAccessFile("001-java-basics/resources/埋埋.jpg","rw");


        byte[] buffer=new byte[1024];
        int len;
        try{
        while ((len=ref1.read(buffer))!=-1){
            ref2.write(buffer,0,len);
        }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(ref1!=null){
                try {
                    ref1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(ref2!=null){
                try {
                    ref2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

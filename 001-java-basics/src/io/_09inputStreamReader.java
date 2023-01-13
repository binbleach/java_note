package io;

import java.io.*;

public class _09inputStreamReader {
    public static void main(String[] args) {
        FileInputStream zj = null;
        InputStreamReader zf = null;
        BufferedReader bf = null;
        try {
            zj = new FileInputStream("InputStreamReader.txt");
            zf = new InputStreamReader(zj); //此时zf是包装流zj是节点流
            bf = new BufferedReader(zf); //此时bf是包装流，zf是节点流

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            if(bf!=null){
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace(); //关闭一个最外层得就好了
                }
            }
        }
    }
}

package io;

import java.io.*;

public class _10BufferedWrite {
    public static void main(String[] args) {
        BufferedWriter fil3 = null;
        try {
            fil3 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("BufferedWrite.text")));
            fil3.write("我是恁爹");
            fil3.write("关我屁事");
            fil3.write("关你屁事");
            fil3.flush();
            fil3.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

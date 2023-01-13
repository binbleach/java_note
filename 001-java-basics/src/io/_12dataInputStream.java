package io;

import java.io.*;

public class _12dataInputStream {
    public static void main(String[] args) {
        DataInputStream data = null;
        try {
            //什么顺序写就得什么顺序读
            data = new DataInputStream(new FileInputStream("DataOutputStream.txt"));
            System.out.println(data.readBoolean());
            System.out.println(data.readByte());
            System.out.println(data.readChar());
            System.out.println(data.readDouble());
            System.out.println(data.readFloat());
            System.out.println(data.readInt());
            System.out.println(data.readLong());
            System.out.println(data.readShort());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(data!=null){
                try {
                    data.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

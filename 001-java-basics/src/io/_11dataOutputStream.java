package io;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class _11dataOutputStream {
    public static void main(String[] args) {
        DataOutputStream data = null;
        try {
            //专属数据输出流，记事本打开是乱码，可以理解为机密的。可以将数据连同数据类型一起写入
            //什么顺序写就得什么顺序读
            data = new DataOutputStream(new FileOutputStream("DataOutputStream.txt"));
            data.writeBoolean(true);
            data.writeByte(1);
            data.writeChar('a');
            data.writeDouble(2.13);
            data.writeFloat(0.4f);
            data.writeInt(123);
            data.writeLong(123);
            data.writeShort(456);
            data.flush();
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

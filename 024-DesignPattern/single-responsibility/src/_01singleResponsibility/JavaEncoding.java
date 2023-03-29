package _01singleResponsibility;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;


/*
    内容讲解：
        1、字符流查码表，字节流不查码表
        2、编码：字符 ->码表->数字
           解码：数字 ->码表->字符  例：50403->gbk->你->unicode->20320
        3、java中字符转换用的是unicode码表，只有流可以用其他的码表：
            例如：(int)'你' =20320或 \u4F60（杠u表示unicode编码，后面为16进制）
        4、java在代码中对流的操作如果不指定编码格式，则使用项目的编码格式
        5、idea文件的编码格式可在右下角或设置里查看，文件可以独立设置编码格式，可以和项目的编码不一样
        6、内容你好转码
            utf-8 -> gbk = 浣犲ソ
            gbk -> utf-8 = ???/???
            设置了 transparent native-to-ascii conversion，配置文件将会中文转为unicode码值：\u4F60\u597D
            utf-8 码表：%u4F60%u597D
            gbk   码表：你好
           写字板 默认编码格式是 asni（本地）

*/
public class JavaEncoding {
    public static void main(String[] args) throws IOException {
        //gbk 一个汉字两个字节
        Reader fileReader = new FileReader("024-DesignPattern/gbk.txt");
        int read = fileReader.read();
        System.out.println(read);
        System.out.println((char)read);
        read = fileReader.read();
        System.out.println(read);
        System.out.println((char)read);
        read = fileReader.read();
        System.out.println(read);
        System.out.println((char)read);
        fileReader.close();
        System.out.println("======================");
        //utf-8 一个汉字三个字节
        Reader fileReader2 = new FileReader("024-DesignPattern/utf-8.txt");
        char cb[] = new char[1];
        int read2 = fileReader2.read(cb);
        System.out.println(read2);
        System.out.println(cb);
        read2 = fileReader2.read(cb);
        System.out.println(read2);
        System.out.println(cb);
        read2 = fileReader2.read(cb);
        System.out.println(read2);
        System.out.println(cb);
        fileReader2.close();

        System.out.println("========================");
        String hello = "你好a";
        System.out.println("字符串："+hello);
        System.out.println("字符'你'的unicode编号："+(int)'你');    //20320 -> 0100 111101 100000
        //字符串‘你’通过utf-8编码后：11100100 10111101 10100000
        // gbk编码中文字符占2字节，英文字符占1字节
        byte[] gbk =  hello.getBytes("gbk");
        System.out.println("gbk编码："+Arrays.toString(gbk));
        System.out.println("    1）gbk解码："+new String(gbk,"gbk"));
        System.out.println("    1）utf-8解码："+new String(gbk,"utf-8"));
        System.out.println("    2）unicode解码："+new String(gbk,"unicode"));
        System.out.println("    3）ascii解码："+new String(gbk,"ascii"));
        //utf-8编码中文字符占3字节，英文字符1字节
        byte[] utf8 =  hello.getBytes("utf-8");
        System.out.println("utf-8编码："+Arrays.toString(utf8));
        System.out.println("    1）utf-8解码："+new String(utf8,"utf-8"));
        System.out.println("    2）gbk解码："+new String(utf8,"gbk"));
        System.out.println("    3）unicode解码："+new String(utf8,"unicode"));
        System.out.println("    4）ascii解码："+new String(utf8,"ascii"));
        //unicode编码中文字符占2字节，英文字符占2字节
        byte[] unicode =  hello.getBytes("unicode");
        System.out.println("unicode编码："+Arrays.toString(hello.getBytes("unicode")));
        System.out.println("    1）unicode解码："+new String(unicode,"unicode"));
        System.out.println("    2）gbk解码："+new String(unicode,"gbk"));
        System.out.println("    3）utf-8解码："+new String(unicode,"utf-8"));
        System.out.println("    4）ascii解码："+new String(unicode,"ascii"));
        //ascii编码英文字符占1个字节
        byte[] ascii =  hello.getBytes("ascii");
        System.out.println("ascii编码："+Arrays.toString(hello.getBytes("ascii")));
        System.out.println("    1）ascii解码："+new String(ascii,"ascii"));
        System.out.println("    2）gbk解码："+new String(ascii,"gbk"));
        System.out.println("    3）utf-8解码："+new String(ascii,"utf-8"));
        System.out.println("    4）unicode解码："+new String(ascii,"unicode"));

        byte[] gbkb = hello.getBytes("gbk"); //gbk
        String utf8s = new String(gbk);                   //gbk->项目编码(utf-8)
        System.out.println("只在编码时设置："+utf8s);

        byte[] utf8b = hello.getBytes();                  //项目编码(utf-8)
        String gbks = new String(utf8b,"gbk"); //项目编码(utf-8)->gbk
        System.out.println("只在解码时设置："+gbks);

        Charset charset = Charset.forName("gbk");       //gbk ->utf-8
        Charset charset2 = Charset.forName("utf-8");
        ByteBuffer byteBuffer = charset.encode(hello);
        System.out.println(byteBuffer);
        CharBuffer charBuffer = charset2.decode(byteBuffer);
        System.out.println(charBuffer);


    }
}

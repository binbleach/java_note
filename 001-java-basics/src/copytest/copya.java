package copytest;

import java.io.*;
import java.util.Date;
import java.util.Scanner;

public class copya {
    public static void main(String[] args) {
        Scanner a = new Scanner(System.in);
        String b =a.next();
        System.out.println(b);
        copylist("D:\\文件\\python数据分析\\pandas");


    }
    public static void copylist(String dirsrc){
        int i = 0;
        File dir = new File(dirsrc);
        File copydir = new File("D:\\文件\\python数据分析\\pandas2");
        if(copydir.exists()== false){
            copydir.mkdir();
        }
        File filelist[] = dir.listFiles();

        for (File file : filelist) {
            i++;
            System.out.println(file);
            String path=file.getAbsolutePath();
            try {
                FileInputStream a = new FileInputStream(path);
                FileOutputStream b = new FileOutputStream(("D:\\文件\\python数据分析\\pandas2\\"+i+"、"+file.getName()));

                byte arr[]  =new byte[1000];
                int readCount = 0;
                while((readCount = a.read(arr))!=-1){
                    b.write(arr,0,readCount);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //path.substring();截取多少个字符
            //path.endsWith("a")?ture:flase 判断是否以a结尾
        }
    }
}

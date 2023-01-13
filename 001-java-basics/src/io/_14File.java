package io;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
Fil类和四大家族没关系，不能完成文件的读和写
只是文件和目录路径名的抽象表示形式,可能是目录可能是文件
需要掌握File类的方法
*/
public class _14File {
    public static void main(String[] args) {
        //单个文件目录mkdir是可以的
        File fli = new File("D:/Java/IDEA_WorkSpece/JavaSE Note");//创建File对象
        System.out.println("file是否存在："+fli.exists()); //是否存在
        if(fli.exists()==false){
            boolean a=fli.mkdir(); //以目录的形式创建，只能建一级没有的JavaSE Note,如果后面有多级没创建的目录则创建失败返回false
            System.out.println(a);

        }

        //多个文件目录需要mkdirs
        File file2 = new File("D:\\Java\\IDEA_WorkSpece\\JavaSE Note\\file2\\a\\b\\c");
        System.out.println(file2.exists());
        if(file2.exists()==false){
            file2.mkdirs(); //可以建多级没有的
        }

        File file3 = new File("D:\\Java\\IDEA_WorkSpece\\JavaSE Note\\file3.txt");
        System.out.println("file3文件是否存在:"+(file3.exists()?"存在":"不存在"));
        if(file3.exists()==false){
            try {
                file3.createNewFile(); //以文件的形式创建,前面不能有不存在文件夹
                System.out.println("file3文件创建成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String parentPath = file3.getParent(); //.getParent()返回的是父目录路径字符串类型
        System.out.println("file3的文件绝对路径是"+file3.getAbsolutePath());
        System.out.println("file3的父目录路径是："+parentPath);
        File file4 = file3.getParentFile();  //.getParentFile 父目录对象
        System.out.println("file3父目录file4的绝对路径"+file4.getAbsolutePath());//绝对路径

        File fli6 = new File("D:\\Java\\IDEA_WorkSpece\\JavaSE Note\\file3.txt"); //不会创建文件
        System.out.println(fli6.getAbsolutePath());//绝对路径
        System.out.println(fli6.getPath());  //相对路径主要看你传的参数是啥就是啥
        System.out.println(fli6.getName());  //获取文件名,带后缀的
        System.out.println(fli6.isDirectory());//是否为目录 false
        System.out.println(fli6.isFile()); //是否为文件     true
        System.out.println("是否为空"+fli6.exists()); //是否存在

        Long a = file3.lastModified(); //返回的是最后修改时间的毫秒数至1970年的
        System.out.println("file3文件最后修改时间(Long):"+a);
        Date time = new Date(a);
        SimpleDateFormat times = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String b= times.format(time);
        System.out.println("ile3文件最后修改时间(String):"+b);

        System.out.println("file3文件的大小(字节)："+file3.length()); //返回文件的大小返回的是字节

        File file7 = new File("D:\\Java\\IDEA_WorkSpece\\JavaSE Note");
        File []f = file7.listFiles();//返回的是数组，就是当前文件下的所有子文件
        for (File file : f) {
            System.out.println(file.getAbsolutePath());
        }

    }
}

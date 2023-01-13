package com.huangjiabin.fastdfs.util;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/*
    套路型代码
*/
public class FastDFSUtil {
    public static String[] upload(byte[] buffFile,String fileExtName){
        StorageClient sc =null;
        try {
            //读取FastDFS的配置文件用于将所以的tracker的地址读取到内存中
            ClientGlobal.init("fastdfs-java.conf");
            TrackerClient tc = new TrackerClient();
            TrackerServer ts = tc.getTrackerServer(); //新版的是这样，旧版本的是.getConnection();
            StorageServer ss = tc.getStoreStorage(ts);
            //创建Storage客户端对象，利用这个对象来完成FastDFS的操作，例如文件上传下载和删除
            sc = new StorageClient(ts,ss);

            /*
                上传文件
                    参数1 为需要上传的文件字节数组
                    参数2 文件扩展名
                    参数3 为文件的属性文件，通常为null即可
                    返回值为一个String数组，这个数组非常中要必须妥善保管，数组里元素1为文件所在组，2为文件远程文件名
            */
            String [] result=sc.upload_file(buffFile,fileExtName,null);
            return result;


        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
            if(sc!=null){
                try {
                    sc.close();  //旧版是ss ts需要关闭
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }
    public static byte[] download(String groupName,String remoteFileName){
        StorageClient sc =null;
        try {
            //读取FastDFS的配置文件用于将所以的tracker的地址读取到内存中
            ClientGlobal.init("fastdfs-java.conf");
            TrackerClient tc = new TrackerClient();
            TrackerServer ts = tc.getTrackerServer(); //新版的是这样，旧版本的是.getConnection();
            StorageServer ss = tc.getStoreStorage(ts);
            //创建Storage客户端对象，利用这个对象来完成FastDFS的操作，例如文件上传下载和删除
            sc = new StorageClient(ts,ss);

            /*
                下载文件
                    参数1 需要下载的文件所在组名
                    参数2 为需要下载的文件远程名
                    参数3 为文件需要保存的本地文件绝对路径
                    返回值为一个int 类型整数，0 表示成功，其他表示文件不存在

            */
            byte[] bytes = sc.download_file(groupName,remoteFileName);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
            if(sc!=null){
                try {
                    sc.close();  //旧版是ss ts需要关闭
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }




    public static void delete(String groupName,String remoteFileName){
        StorageClient sc =null;
        try {
            //读取FastDFS的配置文件用于将所以的tracker的地址读取到内存中
            ClientGlobal.init("fastdfs-java.conf");
            TrackerClient tc = new TrackerClient();
            TrackerServer ts = tc.getTrackerServer(); //新版的是这样，旧版本的是.getConnection();
            StorageServer ss = tc.getStoreStorage(ts);
            //创建Storage客户端对象，利用这个对象来完成FastDFS的操作，例如文件上传下载和删除
            sc = new StorageClient(ts,ss);

            /*
                删除文件
                    参数1 需要下载的文件所在组名
                    参数2 为需要下载的文件远程名
                    返回值为一个int 类型整数，0 表示成功，其他表示文件不存在

            */
            int num = sc.delete_file(groupName,remoteFileName);
            System.out.println(num);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
            if(sc!=null){
                try {
                    sc.close();  //旧版是ss ts需要关闭
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

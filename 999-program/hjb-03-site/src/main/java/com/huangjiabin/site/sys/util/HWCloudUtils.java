package com.huangjiabin.site.sys.util;

import com.obs.services.ObsClient;
import com.obs.services.model.ObsObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/*
    华为obs存储
*/
public class HWCloudUtils {
    private static final String endPoint = "obs.cn-north-4.myhuaweicloud.com";
    private static final String ak = "QB60APMQN15DIPODIIJS";
    private static final String sk = "nXuF5XmGu2F7YJQ1RZnlNoqePLtUCO18vgXoa78D";
    public static String BUCKET_NAME = "activity-place";//桶名
    public static final String codeUrl = "https://yzm.apistore.huaweicloud.com/qxt_sms";
    //文件上传
    public static void ObsUpload(String bucketName, String key, InputStream inputStream) throws IOException {
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        obsClient.putObject(bucketName, key, inputStream);
        obsClient.close();
    }
    //控制层可调用该方法实现文件下载
    public static void dowloadFile(HttpServletResponse response, InputStream inputStream, String fileName) {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] items = new byte[1024 * 10];
        int i = 0;
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            OutputStream outputStream = response.getOutputStream();
            BufferedOutputStream outputStream1 = new BufferedOutputStream(outputStream);
            while ((i = bufferedInputStream.read(items)) != -1) {
                outputStream1.write(items, 0, i);
                outputStream1.flush();
            }
            outputStream1.close();
            outputStream.close();
            bufferedInputStream.close();
            inputStream.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }
    public static ObsObject getFile(String bucketName,String fileName){
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak,sk,endPoint);
        ObsObject obsObject = obsClient.getObject(bucketName, fileName);
        return obsObject;
    }

    //发送短信
    public static String sendCode(String mobile, String content, HttpServletRequest request){
        URL url = null;
        HttpURLConnection connection = null;
        String  result = "";
        try {
            url = new URL(codeUrl+"?mobile="+mobile+"&content="+content);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("POST");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            connection.setRequestProperty("Authorization", "SDK-HMAC-SHA256");
            connection.setRequestProperty("AppKey","9095519b2cfe4c0291ce0a160ca7a070");
            connection.setRequestProperty("AppSecret","6ee9f0eea7a544b49ee026f2d5c446c3");
            // 发送请求
            connection.connect();
            //获取URLConnection对象对应的输入流
            InputStream is = connection.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            if ((result=br.readLine())!=null){
                System.out.println(result);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}

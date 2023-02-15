package com.huangjiabin.site.sys.controller;

import com.huangjiabin.site.sys.model.AjaxResult;
import com.huangjiabin.site.sys.rtsp.MediaVideoTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Controller
public class SurveillanceController {
    //不设置默认是0
    AtomicInteger sign = new AtomicInteger();
    ConcurrentHashMap<Integer, String> pathMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Integer, PipedOutputStream> outputStreamMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Integer, PipedInputStream> inputStreamMap = new ConcurrentHashMap<>();

    @GetMapping("/")
    public String indexView() {
        return "index";
    }

    @GetMapping("/test")
    public String testView() {
        return "test";
    }

    //输入地址点确定，path为地址
    @PostMapping("/putVideo")
    @ResponseBody
    public AjaxResult putVideoPath(String path) {
        try {
            //底层getAndAddInt(this, valueOffset, 1)
            int id = sign.getAndIncrement();
            pathMap.put(id, path);
//            PipedOutputStream和PipedInputStream是管道输出流和管道输入流，配合使用可以实现线程间通信。
            PipedOutputStream pipedOutputStream = new PipedOutputStream();
            PipedInputStream pipedInputStream = new PipedInputStream();
            pipedOutputStream.connect(pipedInputStream);
            outputStreamMap.put(id, pipedOutputStream);
            inputStreamMap.put(id, pipedInputStream);
            return AjaxResult.success(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AjaxResult.error();
        }
    }

    //确定 id为上面传来的线程标识
    @GetMapping("/getVideo")
    public void getVideo(HttpServletRequest request, HttpServletResponse response, int id) {
        log.info("进来了" + id);
        String path = pathMap.get(id);  //获取地址
        String fileName = UUID.randomUUID().toString();
        // 用于测试的时候，本地文件读取走这里
        if (path.endsWith(".mp4")) {
            //split=["好好说再见","mp4"]
            String[] split = new File(path).getName().split("\\.");
            fileName = split[0];
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".flv");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("D:/杂七杂八/"+
                    DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now())+".flv");
            ServletOutputStream outputStream = response.getOutputStream();
            write(id, outputStream,fileOutputStream);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            inputStreamMap.remove(id);
            outputStreamMap.remove(id);
        }
    }

    private void write(int id, OutputStream outputStream,FileOutputStream fileOutputStream) {
        try {
            String path = pathMap.get(id);
            PipedOutputStream pipedOutputStream = outputStreamMap.get(id);
            new Thread(() -> {
                //获取视频类
                MediaVideoTransfer mediaVideoTransfer = new MediaVideoTransfer();
                mediaVideoTransfer.setOutputStream(pipedOutputStream);
                mediaVideoTransfer.setRtspTransportType("udp");
//                mediaVideoTransfer.setRtspTransportType("tcp");
                mediaVideoTransfer.setRtspUrl(path);
                mediaVideoTransfer.start();
            }).start();
            //输出
            print(inputStreamMap.get(id), outputStream,fileOutputStream);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            close(inputStreamMap.get(id), outputStreamMap.get(id), outputStream);
        }
    }

    private void print(InputStream inputStream, OutputStream outputStream,FileOutputStream fileOutputStream){
        try {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, length);
                outputStream.write(buffer, 0, length);
            }
            fileOutputStream.flush();
            outputStream.flush();
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }finally {
            try {
                fileOutputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        SurveillanceController indexController = new SurveillanceController();
        AjaxResult ajaxResult = indexController.putVideoPath("D:/杂七杂八/好好说再见.mp4");
        //indexController.write((int) ajaxResult.get("data"), new FileOutputStream(LocalDateTime.now()+".flv"));
    }
}

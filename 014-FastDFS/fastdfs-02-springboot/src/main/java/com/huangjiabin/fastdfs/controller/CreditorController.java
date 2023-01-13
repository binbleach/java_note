package com.huangjiabin.fastdfs.controller;

import com.huangjiabin.fastdfs.model.CreditorFile;
import com.huangjiabin.fastdfs.model.CreditorInfo;
import com.huangjiabin.fastdfs.service.CreditorService;
import com.huangjiabin.fastdfs.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class CreditorController {
    @Autowired
    private CreditorService creditorService;

    @RequestMapping("/eee")
    public String showCreditorList(Model model){
        List<CreditorInfo> list = creditorService.getCreditorList();
        model.addAttribute("creditorList",list);
        return "index";
    }
    @RequestMapping(value = "/toupload")
    public String toUpload(Model model,Integer creditorId){
        model.addAttribute("creditorId",creditorId);
        return "toUpload";
    }
    @RequestMapping("/upload")
    public String upload(Integer creditorId, MultipartFile uploadFile,Model model){
        //用01项目工具类的方法上传得先先到磁盘再上传太麻烦,得改
        /*
            执行文件上传，实际工作时可能需要限制上传文件的类型
                可以根据uploadFile中获取的文件类型判断，
                也可以根据大小进行拦截
        */
        String fileName = uploadFile.getOriginalFilename();
        try {
            String fileExtName ="";
            int index=fileName.lastIndexOf(".");
            if(index>=0 && index<=fileName.length()-1){
                //进入if表示文件名有点且不是最后一个：有拓展名
                fileExtName = fileName.substring(fileName.lastIndexOf(".")+1);
            }
            String strs[]=FastDFSUtil.upload(uploadFile.getBytes(),fileExtName);
            for(String str:strs){
                System.out.println(str);
            }
            CreditorFile creditorFile = new CreditorFile();
            creditorFile.setCiId(creditorId);
            creditorFile.setGroupName(strs[0]);
            creditorFile.setRemoteFileName(strs[1]);
            creditorFile.setOldFilename(uploadFile.getOriginalFilename());
            creditorFile.setFileSize(uploadFile.getSize());
            creditorFile.setFileType(uploadFile.getContentType());
            creditorService.addCreditorFile(creditorFile);



            System.out.println(uploadFile.getContentType());
            System.out.println(uploadFile.getName());//表单名
            System.out.println(uploadFile.getSize()); //大小
            System.out.println(uploadFile.isEmpty());
            System.out.println(uploadFile.getOriginalFilename());//文件名
            System.out.println(uploadFile.getInputStream()); //输入流
            System.out.println(uploadFile.getBytes()); //字节数组
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("msg","文件上传成功点击确定返回");
        model.addAttribute("url","/eee");
        return "success";
    }

    @RequestMapping("/delete")
    public @ResponseBody String delete(Integer creditorFileId){
        creditorService.deleteFileById(creditorFileId);
        return "success";
    }

    /*
        下载文件返回值为ResponseEntity,这个对象是spring的响应封装对象，这个对象可以包含响应的编码响应头和响应数据
        可以利用这个对象为浏览器返回响应内容
            注意：
                这个对象的泛型决定具体返回的数据类型，byte[]通常用于返回文件流
    */
    @RequestMapping("/download")
    public  ResponseEntity<byte[]> download(Integer creditorFileId,Model model){
        CreditorFile creditorFile=creditorService.getCreditorFileById(creditorFileId);
        //原来的下载不满足我们的需求，需要改，去掉下载后的路径名，返回需要下载的文件数据流，
        byte[] buffFile=FastDFSUtil.download(creditorFile.getGroupName(),creditorFile.getRemoteFileName());
        //创建响应头对象
        HttpHeaders httpHeaders = new HttpHeaders();
        //执行响应头的类型文件夹流类型，这样无论返回什么类型的文件浏览器都会弹出另存为的对话框
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //设置响应的内容大小，这样下载时就可以提供下载进度
        httpHeaders.setContentLength(creditorFile.getFileSize());
        //指定文件下载时的默认文件名称，使用文件上传前的文件名称
        httpHeaders.setContentDispositionFormData("attachment",creditorFile.getOldFilename());
        /*
            使用有参构造创建响应封装对象
            参数 1 为本次响应的具体数据，类型取决于泛型类型，byte[]类型通常用于返回文件
            参数 2 为响应的头文件信息
            参数 3 为本次响应的状态码例如200，400，500
        */
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(buffFile,httpHeaders, HttpStatus.OK);
        return responseEntity;
    }

}

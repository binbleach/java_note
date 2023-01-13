import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //判断请求是否是MultiPart请求
        if(!ServletFileUpload.isMultipartContent(req)){
            throw new RuntimeException("当前请求不是MultiPart请求");
        }

        try {
            //创建一个fileItem工厂，fileItem表示每一个表单项，也就是名字，姓名，照片
            DiskFileItemFactory factory = new DiskFileItemFactory();

            //设置使用临时文件的边界值，大于该值文件会先保存在临时文件钟，否则文件将直接写入到内存.
            //单位字节，这里设置成1M
            factory.setSizeThreshold(1024*1024*1);

            //设置临时文件存放位置，这里是/out/artifacts/11_war_exploded/XXXTemp文件里
            // 默认在tomcat安装目录的temp文件夹中
            factory.setRepository(new File(this.getServletContext().getRealPath("/")+"/XXXTemp"));

            //创建文件上传核心组件
            ServletFileUpload servletFileUpload = new ServletFileUpload(factory);

            //可以防止上传的文件文件名中文乱码（不用设置这个，tomcat里设置-Dfile.encoding=UTF-8一样，还可以防止控制台乱码）
            servletFileUpload.setHeaderEncoding("UTF-8");

            //设置单个文件上传的最大值，超出会报异常，文件上传会失败
            servletFileUpload.setFileSizeMax(1024*1024*1);

            //设置所有文件上传的最大值，超出会报异常，文件上传会失败
            servletFileUpload.setSizeMax(1024*1024*2);

            //解析请求中所有的fileItem
            List<FileItem> fileItems = servletFileUpload.parseRequest(req);
            //这个可以解析FormData中相同名称的元素
            Map<String, List<FileItem>> stringListMap = servletFileUpload.parseParameterMap(req);            //遍历fileItemsz
            for(FileItem item:fileItems){
                //判断item是否是普通的表单项（非 type="file"的表单项）、
                if(item.isFormField()){
                    String fieldName = item.getFieldName();  //获取表单项的名
                    String value = item.getString("UTF-8"); //获取表单项的值
                    System.out.println(fieldName+"=="+value);
                }else {
                    String name = item.getName();   //获取上传文件的原始名
                    System.out.println("上传文件的原始名:"+name);
                    InputStream inputStream = item.getInputStream();
                    //1、在/out/artifacts/11_war_exploded文件
//                    String realPath = this.getServletContext().getRealPath("/");
                    //2、绝对路径
//                    String realPath = "D:/Java/IDEA_WorkSpece/JavaSE_Note";
//                    File file = new File(realPath, name);
//                    OutputStream outputStream = new FileOutputStream(file);
                    //3、绝对路径
                    //OutputStream outputStream = new FileOutputStream("D:/Java/IDEA_WorkSpece/JavaSE_Note/FileUpload/"+System.currentTimeMillis()+name);
                    //4、D盘
                    OutputStream outputStream = new FileOutputStream("/wangyulintest");
                    //5、相对路径是apacheTomcat安装目录的bin路径
//                    OutputStream outputStream = new FileOutputStream("kkkkkkkkkkkkkk");
                    //6一天创建一个文件夹来存放
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String day = simpleDateFormat.format(date);

                    int len;
                    byte[] bytes = new byte[1024];
                    while ((len=inputStream.read(bytes))!=-1){
                        outputStream.write(bytes,0,len);
                    }
                    outputStream.close();
                    inputStream.close();

                    //删除临时文件
                    item.delete();
                }
            }

        } catch (FileUploadException e) {
            e.printStackTrace();
        }


    }
}

package com.huangjiabin.mimissm.controller;

import com.github.pagehelper.PageInfo;
import com.huangjiabin.mimissm.mapper.ProductInfoMapper;
import com.huangjiabin.mimissm.pojo.ProductInfo;
import com.huangjiabin.mimissm.pojo.vo.ProductInfoVo;
import com.huangjiabin.mimissm.service.ProductInfoService;
import com.huangjiabin.mimissm.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoController {
    //每页显示记录数
    public static final int PAGE_SIZE=5;
    //异步上传文件名称
    String saveFileName="";
    //service层对象
    @Autowired
    ProductInfoService productInfoService;

    //显示全部商品不分页
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list=productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }
    //默认显示第一页的五条记录
    @RequestMapping("/split")
    public String split(HttpServletRequest request,Model model){
        Object vo=request.getSession().getAttribute("prodVo");
        PageInfo pageInfo=null;
        if(vo!=null){
             pageInfo=productInfoService.selectCondition((ProductInfoVo)vo,PAGE_SIZE);
        }else {
            //得到第一页数据
            pageInfo = productInfoService.splitPage(1,PAGE_SIZE);
        }
        request.getSession().removeAttribute("prodVo");
        model.addAttribute("info",pageInfo);
        return "product";
    }

    //ajax分页翻页处理
    @ResponseBody
    @RequestMapping("/ajaxsplit")
    public void ajaxSplit(ProductInfoVo vo, HttpSession session){
        //取得当前Page参数的页面数据
        PageInfo info = productInfoService.selectCondition(vo,PAGE_SIZE);
        session.setAttribute("info",info);
    }

    //文件上传
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request) throws IOException {
        //提取生成文件名UUID+上传图片的后缀名
        saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());

        //得到项目中图片存储路径
        String path = request.getServletContext().getRealPath("/image_big");

        /*
            pimage.transferTo ：转存
            File.separator    ： 反斜杠"\" 保证任何系统都不出错 linux:"/" windows:"\"
        */
        pimage.transferTo(new File(path+File.separator+saveFileName));

        //这一步属实多余，改成直接返回String，然后记得该ajax接收的类型为text。不过error也要改，不建议改
        JSONObject object = new JSONObject();
        object.put("imgurl",saveFileName);

        //返回src路径用于回显图片
        return object.toString();

    }

    //新增操作
    @RequestMapping("/save")
    public String save(ProductInfo info,HttpServletRequest request){
        info.setpImage(saveFileName);
        info.setpDate(new Date());
        int num=-1;
        num=productInfoService.save(info);
        if(num>0){
            request.setAttribute("msg","增加成功");
        }else {
            request.setAttribute("msg","增加失败");
        }
        saveFileName="";
        return "forward:/prod/split.action";
    }

    //更新前信息回显操作
    @RequestMapping("/one")
    public String one(int pid,ProductInfoVo vo,Model model,HttpSession session){
        ProductInfo info = productInfoService.getById(pid);
        model.addAttribute("prod",info);
        session.setAttribute("prodVo",vo);
        return "update";
    }

    //更新操作
    @RequestMapping("/update")
    public String update(ProductInfo info,HttpServletRequest request){
        //因为ajax的异步图片上传，如果有上传过图片，则saveFileName里有上传上来的图片名，
        // 如果没有使用异步ajax上传过图片saveFileName则为空。实体类info使用隐藏表单域提供上来的pImage原始图片名称
        if(!saveFileName.equals("")){
            info.setpImage(saveFileName);
        }
        //完成更新操作
        int num=-1;
        num = productInfoService.update(info);
        if(num>0){
            //更新成功
            request.setAttribute("msg","更新成功");
        }else {
            //更新失败
            request.setAttribute("msg","更新失败");
        }

        saveFileName = "";
        return "forward:/prod/split.action";
    }

    //异步删除单个
    @RequestMapping("/delete")
    public String delete(int pid,ProductInfoVo vo,HttpServletRequest request){
        int num=-1;
        num=productInfoService.delete(pid);
        if(num>0){
            request.setAttribute("msg","删除成功");
            request.getSession().setAttribute("deleteProdVo",vo);
        }else {
            request.setAttribute("msg","删除失败");
        }
        return "forward:/prod/deleteSplit.action";

    }

    //删除后做分页显示
    @RequestMapping(value = "/deleteSplit",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteSplit(HttpServletRequest request){
        PageInfo info = null;
        ProductInfoVo vo = (ProductInfoVo) request.getSession().getAttribute("deleteProdVo");
        if(vo!=null) {
            info = productInfoService.selectCondition(vo,PAGE_SIZE);
        }else {
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.getSession().setAttribute("info",info);
        String msg=(String)request.getAttribute("msg");
        return msg;
    }

    //批量删除商品
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids,HttpServletRequest request){

        String [] ps=pids.split(",");
        int num=productInfoService.deleteBatch(ps);
        if(num>0){
            request.setAttribute("msg","批量删除成功");
        }else {
            request.setAttribute("msg","批量删除失败");
        }
        return "forward:/prod/deleteSplit.action";
    }
    @RequestMapping("/selectCondition")
    @ResponseBody
    public void selectCondition(ProductInfoVo vo,HttpSession session){
        //List<ProductInfo> list = productInfoService.selectCondition(vo);
        //session.setAttribute("info",list);
    }


}

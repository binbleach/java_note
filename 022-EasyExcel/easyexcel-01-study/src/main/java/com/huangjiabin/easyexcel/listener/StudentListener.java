package com.huangjiabin.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.huangjiabin.easyexcel.po.Student;

//读取监听器
public class StudentListener extends AnalysisEventListener<Student> {
    //每读取一行内容，都会调用一次invoke，在invoke可以操作使用读取到的数据
    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        System.out.println("student="+student);
    }

    //读取完整个文档之后调用方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("读取完毕...");
    }
}

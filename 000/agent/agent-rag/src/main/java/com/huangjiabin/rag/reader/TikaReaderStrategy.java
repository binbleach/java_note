package com.huangjiabin.rag.reader;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/*
    通用文件内容提取工具（POI、PDFBox、Jsoup、OpenCV 等）
    支持格式
    文档：PDF、Word (doc/docx)、Excel (xls/xlsx)、PPT、TXT、Markdown、RTF
    网页：HTML、xhtml（内部集成 Jsoup）
    图片：jpg/png/gif/tiff（OCR 提取图片文字）
    压缩包：zip、rar、7z（读取内部嵌套文件）
    邮件：eml、msg、mbox
    其他：epub、csv、json
*/
@Service
public class TikaReaderStrategy implements DocumentReaderStrategy {
    @Override
    public boolean supports(File file) {

        String name = file.getName().toLowerCase();
        return name.endsWith(".doc") || name.endsWith(".docx");
    }

    @Override
    public List<Document> read(File file) throws IOException {
        Resource resource = new FileSystemResource(file);
        return new TikaDocumentReader(resource).get();
    }
}

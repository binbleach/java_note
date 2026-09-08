package com.huangjiabin.rag.controller;


import com.huangjiabin.rag.embedding.EmbeddingService;
import com.huangjiabin.rag.reader.DocumentReaderFactory;
import com.huangjiabin.rag.splitter.OverlapParagraphTextSplitter;
import com.huangjiabin.rag.utils.DocumentCleanUtils;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/*
*   文档向量化
*/
@RestController
@RequestMapping("/rag/embedding")
public class RagEmbeddingController {

    @Autowired
    private EmbeddingModel embeddingModel;

    //测试看embeddingModel注入的是哪个模型的（DashScope的）
    @RequestMapping("/test")
    public String test() {
        for (float i : embeddingModel.embed("test")) {
            System.out.println(i);
        }
        return "success";
    }

    @Autowired
    private DocumentReaderFactory documentReaderFactory;
    @Autowired
    private EmbeddingService embeddingService;

    @RequestMapping("embed")
    public String embed(String filePath) {

        // 读取文件
        List<Document> documents;
        try {
            documents = documentReaderFactory.read(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //清洗并分段
        List<Document> allChunkedDocuments = DocumentCleanUtils.cleanDocuments(documents).stream()
                .flatMap(document -> {
                    //自定义的滑动窗口分块
                    OverlapParagraphTextSplitter splitter = new OverlapParagraphTextSplitter(1000, 50);
                    return splitter.split(document).stream();
                })
                .collect(Collectors.toList());

        //向量化并存储
        embeddingService.embedAndStore(DocumentCleanUtils.cleanDocuments(allChunkedDocuments));

        return "success";
    }
}

package com.huangjiabin.rag.controller;

import com.huangjiabin.rag.reader.DocumentReaderFactory;
import com.huangjiabin.rag.utils.DocumentCleanUtils;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

/*
    文档读取
*/
@RestController
@RequestMapping("/rag")
public class RagReaderController {

    @Autowired
    private DocumentReaderFactory documentReaderFactory;

    @RequestMapping("/read")
    public String read(String filePath) {
        List<Document> documents;
        try {
            documents = DocumentCleanUtils.cleanDocuments(documentReaderFactory.read(new File(filePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(documents.size());
        StringBuffer sb = new StringBuffer();
        for (Document document : documents) {
            sb.append(document.getText());
            System.out.println(document.getText());
            System.out.println(document.getMetadata());
            System.out.println("========");
            sb.append("========================");
        }
        return sb.toString();
    }
}

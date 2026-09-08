package com.huangjiabin.rag.controller;

import com.huangjiabin.rag.elasticsearch.ElasticSearchService;
import com.huangjiabin.rag.elasticsearch.EsDocumentChunk;
import com.huangjiabin.rag.reader.DocumentReaderFactory;
import com.huangjiabin.rag.splitter.OverlapParagraphTextSplitter;
import com.huangjiabin.rag.utils.DocumentCleanUtils;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/rag/es")
public class RagEsController {


    @Autowired
    private DocumentReaderFactory selector;

    @Autowired
    private ElasticSearchService elasticSearchService;

    /*
    *   http://localhost:8080/rag/es/write?filePath=D:/Use/Java开发3-5年经验沉稳简历.docx
    */
    @RequestMapping("write")
    public String write(String filePath) throws Exception {
        // 1. 加载文档
        List<Document> documents = selector.read(new File(filePath));

        // 2. 文本清洗
        documents = DocumentCleanUtils.cleanDocuments(documents);

        // 3. 文档分片
        OverlapParagraphTextSplitter splitter = new OverlapParagraphTextSplitter(
                // 每块最大字符数
                200,
                // 块之间重叠 100 字符
                50
        );
        List<Document> apply = splitter.apply(documents);

        // 4. 存储到ES
        List<EsDocumentChunk> esDocs = apply.stream().map(doc -> {
            EsDocumentChunk es = new EsDocumentChunk();
            es.setId(doc.getId());
            es.setContent(doc.getText());
            es.setMetadata(doc.getMetadata());
            return es;
        }).toList();

        elasticSearchService.bulkIndex(esDocs);
        return "success";
    }

    /*
    *   http://localhost:8080/rag/es/search?keyword=南昌易通信息科技有限公司
    */
    @RequestMapping("search")
    public List<EsDocumentChunk> search(String keyword) throws Exception {
        return elasticSearchService.searchByKeyword(keyword);
    }
}

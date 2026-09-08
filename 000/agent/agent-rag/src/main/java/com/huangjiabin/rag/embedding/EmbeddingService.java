package com.huangjiabin.rag.embedding;

import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmbeddingService {
    @Resource
    private EmbeddingModel embeddingModel;

    @Resource
    private VectorStore vectorStore;

    /*
    *   向量化
    */
    public List<float[]> embed(List<Document> documentList){
        return documentList.stream().map(document ->
                embeddingModel.embed(document.getText())).collect(Collectors.toList());
    }

    /*
    *   向量化然后存储
    */
    public void embedAndStore(List<Document> documentList){
        for (int i = 0; i < documentList.size(); i += 9) {
            List<Document> batches = documentList.subList(i, Math.min(i + 9, documentList.size()));
            vectorStore.add(batches);
        }
    }

    private static final int DEFAULT_TOP_K = 5;
    private static final double DEFAULT_SIMILARITY_THRESHOLD = 0.5;

    /*
     *   相似度检索
     */
    public List<Document> similaritySearch(String query){
        return vectorStore.similaritySearch(SearchRequest
                .builder()
                .query(query)
//                .filterExpression()   //元数据过滤表达式（根据metadata过滤，再做向量检索）
                .topK(DEFAULT_TOP_K)    //召回条数
                .similarityThreshold(DEFAULT_SIMILARITY_THRESHOLD)  //相似度阈值（过滤掉相似度低于该数值的向量结果）
                .build());
    }

    public List<Document> similaritySearch(SearchRequest searchRequest) {
        return vectorStore.similaritySearch(searchRequest);
    }


}

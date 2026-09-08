package com.huangjiabin.rag.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.huangjiabin.rag.embedding.EmbeddingService;
import com.huangjiabin.rag.reader.DocumentReaderFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

/*
*   检索优化：元数据过滤检索
*/
@RestController
@RequestMapping("/rag/metadata")
public class RagMetadataController implements InitializingBean {


    @Autowired
    private DocumentReaderFactory documentReaderFactory;

    @Autowired
    private EmbeddingService embeddingService;

    /*
    *   将 fileName设置成元数据，存入向量数据库
    */
    @GetMapping("/embedding")
    public String embedding(String filePath, String fileName) {

        List<Document> documents;
        try {
            documents = documentReaderFactory.read(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Document document : documents) {
            document.getMetadata().put("fileName", fileName);
        }

        embeddingService.embedAndStore(documents);

        return "success";
    }

    /*
    *    过滤检索
    */
    @GetMapping("/retrieveMetadata")
    public String retrieveMetadata(String query, String fileName) {

        SearchRequest searchRequest = SearchRequest.builder().query(query).filterExpression("fileName == '" + fileName + "'").build();

        return embeddingService.similaritySearch(searchRequest).toString();
    }

    @Autowired
    private PgVectorStore vectorStore;

    @Autowired
    private ChatModel chatModel;

    private ChatClient chatClient;

    /*
    *    通过advisor做过滤检索，并增强生成
    */
    @GetMapping("/retrieveAdvisorWithMetadata")
    public String retrieveAdvisorWithMetadata(String query, String fileName) {
        return chatClient.prompt(query)
                .advisors(advisorSpec -> advisorSpec.param("qa_filter_expression", "fileName == '" + fileName + "'"))
                .call().content();
    }


    @Override
    public void afterPropertiesSet() throws Exception {

        // 自定义Prompt模板
        PromptTemplate promptTemplate = new PromptTemplate("""
                请基于以下提供的参考文档内容，回答用户的问题。
                如果参考文档中没有相关信息，请直接说明"没有找到相关信息"，不要编造内容。
                
                参考文档内容:
                {question_answer_context}
                
                用户问题: {query}
                """);

        QuestionAnswerAdvisor questionAnswerAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest.builder().similarityThreshold(0.5).topK(5).build())
                .promptTemplate(promptTemplate).build();

        this.chatClient = ChatClient.builder(chatModel)
                // 实现 Logger 的 Advisor
                .defaultAdvisors(questionAnswerAdvisor)
                // 设置 ChatClient 中 ChatModel 的 Options 参数
                .defaultOptions(
                        DashScopeChatOptions.builder()
                                .withTopP(0.7)
                                .build()
                ).build();
    }
}

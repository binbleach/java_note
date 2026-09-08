package com.huangjiabin.rag.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.generation.augmentation.QueryAugmenter;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.expansion.QueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    模块化rag，
    RetrievalAugmentationAdvisor：
        可自由拼装组件的 RAG 实现，适合做复杂 RAG（查询改写、多查询扩展、重排、GraphRAG 混合检索）
    项	QuestionAnswerAdvisor	RetrievalAugmentationAdvisor
    定位	简单快速 RAG，固定流程	    模块化流水线 RAG，可自定义每一步
    依赖	 spring‑ai‑chat	            需要额外spring‑ai‑rag
*/
@RestController
@RequestMapping("/rag/modular")
public class
ModularRagController implements InitializingBean {

    @Autowired
    ChatModel chatModel;

    ChatClient chatClient;

    @Autowired
    private VectorStore vectorStore;


    @GetMapping("/retriever")
    public String retriever(String query) {

        DocumentRetriever retriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)          // 必需：绑定向量存储
                .topK(5)                             // 返回最相似的 5 个文档
                .similarityThreshold(0.6)            // 相似度低于 0.6 的过滤掉
                .build();

        RewriteQueryTransformer queryTransformer = RewriteQueryTransformer.builder()
                .chatClientBuilder(ChatClient.builder(chatModel))
                .promptTemplate(new PromptTemplate("""
                        Given a user query, rewrite it to provide better results when querying a {target}.
                        
                        Remove any irrelevant information, and ensure the query is concise and specific.
                        
                        如果有表述不清的内容，或者错别字，请修正，如"华子"，修改为"华为"
                        
                        Original query:
                        {query}
                        
                        Rewritten query:
                        """))
                .build();


        QueryExpander queryExpander = MultiQueryExpander.builder()
                .chatClientBuilder(ChatClient.builder(chatModel))
                .numberOfQueries(3)
                .includeOriginal(true)
                .build();

        QueryAugmenter queryAugmenter = ContextualQueryAugmenter.builder()
                .allowEmptyContext(true)    //默认检索为空让大模型不回答，true允许检索为空回答
                .emptyContextPromptTemplate(new PromptTemplate("请回答以下用户问题"))
                //自定义prompt（使用 SpringAI 内置英文 prompt，模板指令很保守，模型偏向摘抄原文，输出看起来很像直接返回向量库内容）
                .promptTemplate(new PromptTemplate("""
                    参考下面的知识库资料，用自己的语言整理总结回答用户问题。
                    不要直接大段复制原文。
                    知识库：
                    {document_context}
                    用户问题：{user_query}
                    """))
                .build();

        RetrievalAugmentationAdvisor advisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(retriever)   //真正检索（向量库 / 自定义，可以自己实现接入 Neo4j 图检索）
                .queryTransformers(queryTransformer) //问题改写（比如 RewriteQueryTransformer，优化用户提问），可多个链式执行
                .queryExpander(queryExpander)   //询扩展（Multi‑Query，一个问题生成多个搜索 query，提升召回）
                .queryAugmenter(queryAugmenter) //把上下文注入 prompt，生成送给 LLM 的最终 prompt；处理检索为空兜底逻辑
                .build();

        return chatClient.prompt(query).advisors(advisor).call().content();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        chatClient = ChatClient.builder(chatModel).defaultAdvisors(new SimpleLoggerAdvisor()).build();
    }
}

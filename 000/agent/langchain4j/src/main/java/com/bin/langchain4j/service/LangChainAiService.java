package com.bin.langchain4j.service;

import com.bin.langchain4j.entity.Book;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

@AiService
public interface LangChainAiService {
    String chat(String message);

    Flux<String> stream(String message);

    @SystemMessage("你是大宾哥的ai助手，回答java开发相关问题")
    @UserMessage("针对用户的内容：{{topic}}，先复述一遍他的问题，然后再回答\"")
    Flux<String> chatTemplate(String topic);

    @UserMessage("请帮我推荐1本java相关的书")
    @SystemMessage("你是一个专业的图书推荐人员")
    Book getBooks();

}

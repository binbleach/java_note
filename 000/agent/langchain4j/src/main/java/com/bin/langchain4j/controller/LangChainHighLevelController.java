package com.bin.langchain4j.controller;

import com.bin.langchain4j.config.RedisChatMemoryStore;
import com.bin.langchain4j.entity.Book;
import com.bin.langchain4j.service.LangChainAiService;
import com.bin.langchain4j.service.LangChainMemoryAiService;
import com.bin.langchain4j.tools.TemperatureTool;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/*
    langchain4j高级：用 @AiService
*/
@RestController
@RequestMapping("/high")
public class LangChainHighLevelController implements InitializingBean {

    @Resource
    private LangChainAiService aiService;
    @Resource
    private ChatModel chatModel;

    private LangChainMemoryAiService langChainMemoryAiService;

    @Resource
    private RedisChatMemoryStore redisChatMemoryStore;

    // 模型记忆配置类
    @Override
    public void afterPropertiesSet() throws Exception {
        langChainMemoryAiService = AiServices.builder(LangChainMemoryAiService.class)
                .chatModel(chatModel)
                //内存记忆
//                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                // redis记忆
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory
                        .builder().id(memoryId).maxMessages(10).chatMemoryStore(redisChatMemoryStore).build())
                .build();
    }

    @RequestMapping("chat")
    public String chat(String message){
        return aiService.chat(message);
    }

    @RequestMapping("stream")
    public Flux<String> stream(String message, HttpServletResponse response){
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return aiService.stream(message);
    }

    @RequestMapping("/template")
    public Flux<String> template(HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return aiService.chatTemplate("我饿了？");
    }

    @RequestMapping("/structure")
    public String structure(HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Book book = aiService.getBooks();
        return book.id()+book.name()+book.author();
    }

    @RequestMapping("/memoryChat")
    public String memoryChat(HttpServletResponse response, String msg, String memoryId) {
        response.setCharacterEncoding("UTF-8");
        return langChainMemoryAiService.chatMemory(memoryId, msg);
    }

    @RequestMapping("tool")
    public String tool(String message, HttpServletResponse response){
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        LangChainAiService aiService1 = AiServices.builder(LangChainAiService.class)
                .tools(new TemperatureTool())
                .chatModel(chatModel).build();
        return aiService1.chat(message);
    }

}

package com.bin.springai.controller;

import com.bin.springai.tools.TimeTools;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
    函数式工具调用
*/
@Slf4j
@RestController
@RequestMapping("/function")
public class FunctionCallController {

    @Resource
    @Qualifier("dashScopeChatModel")
    private ChatModel chatModel;

    private ChatClient chatClient;

    @PostConstruct
    public void init() {
        ChatMemory chatMemory = MessageWindowChatMemory.builder().maxMessages(10).build();

        chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @RequestMapping("/chat")
    public String chat(String message) {
        log.info("chat request => {}", message);

//        return chatClient.prompt().toolNames("getTimeFunction").user(message).call().content();
        return chatClient.prompt().tools(new TimeTools()).user(message).call().content();
    }
}

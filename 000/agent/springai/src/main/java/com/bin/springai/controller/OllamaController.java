package com.bin.springai.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import kotlin.text.Charsets;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/*
    springai调用ollama
*/
@RestController
@RequestMapping("/ollama")
public class OllamaController {
    @Resource
    @Qualifier("ollamaChatModel")
    private ChatModel chatModel;

    @RequestMapping("/chatModel/call")
    public String call(String message){
        return chatModel.call(message);
    }

    @RequestMapping("/chatModel/stream")
    public Flux<String> stream(String message, HttpServletResponse response){
        response.setCharacterEncoding(Charsets.UTF_8.name());
        return chatModel.stream(message);
    }
}

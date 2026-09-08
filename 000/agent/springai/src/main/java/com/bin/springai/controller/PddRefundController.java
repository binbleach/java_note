package com.bin.springai.controller;

import com.bin.springai.entity.ChatStatus;
import com.bin.springai.entity.OrderChat;
import com.bin.springai.tools.OrderTool;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import kotlin.text.Charsets;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.UUID;
import java.util.function.Consumer;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

/*
    模拟拼多多自动退款
*/
@RestController
@RequestMapping("/pdd")
public class PddRefundController {

    @Resource
    @Qualifier("dashScopeChatModel")
    private ChatModel chatModel;    //依赖自动配置

    private ChatClient chatClient;

    @Resource
    private ChatMemory chatmemory;

    @Resource
    private OrderTool orderTool;

    @Value("classpath:templates/pdd_refund_system_prompt.pt")
    private org.springframework.core.io.Resource systemText;

    @PostConstruct
    public void init() {
        chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatmemory).build(), new SimpleLoggerAdvisor())
                .defaultSystem(systemText)
                .build();
    }

    @RequestMapping("/newChat")
    public OrderChat newChat(String userId, String orderId, HttpServletResponse response){
        response.setCharacterEncoding(Charsets.UTF_8.name());

        //模拟数据库创建一个chat的记录，获取到他的唯一id
        String chatId = UUID.randomUUID().toString();

        return chatClient.prompt().user(String.format("我要咨询订单相关的售后问题，我的用户id是%s,我的订单号是: %s ,本地的对话Id是 %s，当前状态是 %s", userId, orderId, chatId, ChatStatus.CHAT_START))
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID,chatId))
                .call().entity(OrderChat.class);
    }

    @RequestMapping("/ask")
    public Flux<String> ask(String question, String chatId, HttpServletResponse response){
        response.setCharacterEncoding(Charsets.UTF_8.name());

        return chatClient.prompt().user(question).tools(orderTool)
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, chatId))
                .stream().content();

    }

}

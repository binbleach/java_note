package com.bin.langchain4j.controller;

import com.bin.langchain4j.tools.WeatherTool;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.data.message.*;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.chat.request.ResponseFormatType;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.request.json.JsonSchema;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.tool.DefaultToolExecutor;
import dev.langchain4j.service.tool.ToolExecutor;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.digester.DocumentProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.xml.stream.events.Characters;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static dev.langchain4j.data.message.SystemMessage.systemMessage;
import static dev.langchain4j.data.message.UserMessage.userMessage;

/*
    langchain4j基础：纯chatModel
*/
@RestController
@RequestMapping("/low")
public class LangChainLowLevelController {
    @Resource
    OpenAiChatModel chatModel;
    @Resource
    OpenAiStreamingChatModel streamingChatModel;

    @RequestMapping("/chat")
    public String chat(String message) {
        return chatModel.chat(message);
    }

    @RequestMapping("/stream")
    public Flux<String> stream(String message, HttpServletResponse response){
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Flux<String> flux = Flux.create(fluxSink ->
            streamingChatModel.chat(message, new StreamingChatResponseHandler() {
                @Override
                public void onPartialResponse(String partialResponse) {
                    fluxSink.next(partialResponse);
                }

                @Override
                public void onCompleteResponse(ChatResponse completeResponse) {
                    fluxSink.complete();
                }

                @Override
                public void onError(Throwable error) {
                    fluxSink.error(error);
                }
            })
        );
        return flux;
    }

    @RequestMapping("/memory")
    public String memory(HttpServletResponse response) {
        List<ChatMessage> messages = new ArrayList<>();

        //第一轮对话
        messages.add(systemMessage("你是一个点餐助手"));
        messages.add(userMessage("给我点一个汉堡，两个鸡腿，一杯可乐"));
        AiMessage answer = chatModel.chat(messages).aiMessage();
        System.out.println(answer);
        System.out.println("======");

        messages.add(answer);

        //第二轮对话
        messages.add(userMessage("刚才菜点多了，去掉一个鸡腿，再加一杯可乐吧?"));
        AiMessage answer1 = chatModel.chat(messages).aiMessage();
        System.out.println(answer1);
        System.out.println("======");

        messages.add(answer1);

        //第三轮对话
        messages.add(userMessage("我现在总共点了哪些东西？"));
        AiMessage answer2 = chatModel.chat(messages).aiMessage();
        System.out.println(answer2);
        System.out.println("======");

        return answer2.text();
    }

    @RequestMapping("/memory2")
    public String memory2(){
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        //第一轮对话
        chatMemory.add(systemMessage("你是一个点餐助手"));
        chatMemory.add(userMessage("给我点一个汉堡，两个鸡腿，一杯可乐"));
        AiMessage answer = chatModel.chat(chatMemory.messages()).aiMessage();
        System.out.println(answer);
        System.out.println("======");

        chatMemory.add(answer);

        //第二轮对话
        chatMemory.add(userMessage("刚才菜点多了，去掉一个鸡腿，再加一杯可乐吧"));
        AiMessage answer1 = chatModel.chat(chatMemory.messages()).aiMessage();
        System.out.println(answer1);
        System.out.println("======");

        chatMemory.add(answer1);

        //第三轮对话
        chatMemory.add(userMessage("我现在总共点了哪些东西？"));
        AiMessage answer2 = chatModel.chat(chatMemory.messages()).aiMessage();
        System.out.println(answer2);
        System.out.println("======");

        return answer2.text();
    }


    /*
    *   结构化输出
    */
    @RequestMapping("/structure")
    public String structure() {
        ResponseFormat responseFormat = ResponseFormat.builder()
                .type(ResponseFormatType.JSON) // type can be either TEXT (default) or JSON
                .jsonSchema(JsonSchema.builder()
                        .name("Person") // OpenAI requires specifying the name for the schema
                        .rootElement(JsonObjectSchema.builder() // see [1] below
                                .addStringProperty("name")
                                .addIntegerProperty("age")
                                .addNumberProperty("height")
                                .addBooleanProperty("married")
                                .required("name", "age", "height", "married") // see [2] below
                                .build())
                        .build())
                .build();
        UserMessage userMessage = UserMessage.from("徐凤年");
        ChatRequest chatRequest = ChatRequest.builder()
                .responseFormat(responseFormat)
                .messages(userMessage)
                .build();
        return chatModel.chat(chatRequest).aiMessage().text();
    }

    @RequestMapping("tool")
    public String tool() {
        List<ChatMessage> chatMessageList = new ArrayList<>();
        // 1. 生成工具定义
        List<ToolSpecification> toolSpecifications = ToolSpecifications.toolSpecificationsFrom(WeatherTool.class);
        // 用户消息
        UserMessage userMsg = UserMessage.from("2026年07月01日，海口的天气怎样？");
        chatMessageList.add(userMsg);
        // 2. 第一轮请求：模型返回工具调用
        ChatRequest req1 = ChatRequest.builder()
                .messages(userMsg)
                .toolSpecifications(toolSpecifications)
                .build();
        ChatResponse resp1 = chatModel.chat(req1);
        AiMessage aiMsg = resp1.aiMessage();
        chatMessageList.add(aiMsg);
        // 3. 判断是否需要执行工具
        if (aiMsg.hasToolExecutionRequests()) {
            // 手动执行工具
//            ToolExecutionRequest toolReq = aiMsg.toolExecutionRequests().get(0);
            List<ToolExecutionRequest> toolReqs = aiMsg.toolExecutionRequests();
            toolReqs.forEach(toolReq -> {

                // 工具执行器，反射调用WeatherTool.getWeather
                DefaultToolExecutor toolExecutor = new DefaultToolExecutor(new WeatherTool(),toolReq);
                String toolResult = toolExecutor.execute(toolReq, null);
                // 封装工具返回消息
                ToolExecutionResultMessage toolMsg = ToolExecutionResultMessage.from(toolReq,toolResult);
                chatMessageList.add(toolMsg);
            });

            //4. 再次调用模型，返回结果
//            ChatResponse finalChatResponse = chatModel.chat(chatMessageList);
//            return finalChatResponse.aiMessage().text();

            // 4. 第二轮请求：把工具结果塞回去，LLM生成最终文字
//            ChatRequest req2 = ChatRequest.builder()
//                    .messages(chatMessageList) // 完整对话上下文
//                    .toolSpecifications(toolSpecifications)
//                    .build();
//            ChatResponse resp2 = chatModel.chat(req2);
//            // 这里才能拿到非null文本
//            return resp2.aiMessage().text();
        }
// 不需要工具，直接返回文本
        return aiMsg.text();
    }

}

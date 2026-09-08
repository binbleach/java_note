package com.bin.springai.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.bin.springai.entity.Book;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import kotlin.text.Charsets;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
    springai-alibaba调用
*/
@RestController
@RequestMapping("/aiAli")
public class SpringAiAlibabaController implements InitializingBean {

    @Resource
    @Qualifier("dashScopeChatModel")
    private ChatModel chatModel;    //依赖自动配置

    private ChatClient chatClient;  // chatClient不会自动配置，需要自己创建，一般会做一些初始化设置。

    @Autowired
    private ChatMemory chatMemory;  //因为配置了jdbcChatMemoryRepository，所以是数据库持久化，默认是内存持久化
    /*
     *    配置 chatClient
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // defaultSystem设置了，.system()再设置会覆盖，SystemMessage不会。最好只设置一个避免混乱
        // ChatMemory可以不用自己创建，直接引入依赖spring-ai-autoconfigure-model-chat-memory，然后注入使用即可。
//        ChatMemory chatMemory = MessageWindowChatMemory.builder().maxMessages(10).build();
        chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())  //模型记忆
                .defaultSystem("你是大宾哥的小助手，回答一些关于他开发的ai程序的一些问题")
                .defaultOptions(
                        DashScopeChatOptions.builder().topP(0.7).build()    //核采样
                )
                .build();
    }

    @RequestMapping("/chatModel/call")
    public String call(String message){
        return chatModel.call(message);
    }

    @RequestMapping("/chatModel/call2")
    public String call2(String message){
        SystemMessage systemMessage = new SystemMessage("你是一个翻译工具，请把用户的话翻译成英文");
        UserMessage userMessage = new UserMessage(message);
        return chatModel.call(systemMessage,userMessage);
    }

    @RequestMapping("/chatModel/call3")
    public String call3(String message){
        UserMessage userMessage = new UserMessage(message);
        ChatOptions build = ChatOptions.builder().model("qwen3.7-max").build();
        Prompt prompt = Prompt.builder().messages(userMessage).chatOptions(build).build();
        return chatModel.call(prompt).getResult().getOutput().getText();
    }

    @RequestMapping("/chatModel/stream")
    public Flux<String> stream(String message, HttpServletResponse response){
        response.setCharacterEncoding(Charsets.UTF_8.name());
        return chatModel.stream(message);
    }

    @RequestMapping("/chatClient/prompt")
    public String prompt(String message){
        //少样本提示词
        return chatClient.prompt(message).system("""
                请你根据用户输入的问题做改写，主要有以下改写策略：
                1、改写其中的错别字。
                2、做内容精简，提炼句子里的关键内容。
                可以参考一下实例：
                示例1：
                输入：泥好
                输出：{错别字改写："你好"，精简内容：""}
                示例2：
                输入：今天天气真好，是一个晴天，没有下雨。
                输出：{错别字改写：""，精简内容："今天是晴天。"}
                """).call().content();
    }

    @RequestMapping("/chatClient/stream")
    public Flux<String> stream2(String message, HttpServletResponse response){
        response.setCharacterEncoding(Charsets.UTF_8.name());
        //设置输出格式
        return chatClient.prompt("请以json的格式输出").stream().content();
    }

    /*
    *   提示词模板管理（说白了就是替换关键字）；还可以用.st文件读取的方式去设置
    */
    @RequestMapping("/promptTemplate")
    public String model(String message){
        String template = "请给我推荐几个关于{topic}的开源项目";
        PromptTemplate promptTemplate = new PromptTemplate(template);
        promptTemplate.add("topic",message);

        return chatClient.prompt(promptTemplate.create()).call().content();
    }

    /*
     *   结构化输出：string 、 bean
     */
    @RequestMapping("/converter")
    public String converter(){
        PromptTemplate promptTemplate = new PromptTemplate("请给我推荐几本悬疑类型的书，输出格式:{format}");
        BeanOutputConverter<Book> converter = new BeanOutputConverter<>(Book.class);
        Prompt prompt = promptTemplate.create(Map.of("format", converter.getFormat()));
        String res = chatClient.prompt(prompt).call().content();
        Book book = converter.convert(res);
        return book.id() + " " + book.author() + " " + book.name() + " " + book.intro();
    }

    /*
     *   结构化输出正常写法
     */
    @RequestMapping("/converter2")
    public String converter2(){
        Book book = chatClient.prompt("请给我推荐几本悬疑类型的书").call().entity(Book.class);
        return book.id() + " " + book.author() + " " + book.name() + " " + book.intro();
    }

    /*
     *   结构化输出：list<bean>
     */
    @RequestMapping("/converter3")
    public String converter3(){
        List<Book> book = chatClient.prompt("请给我推荐几本悬疑类型的书").call()
                .entity(new ParameterizedTypeReference<>() {});
        return book.toString();
    }

    /*
     *   结构化输出：Map
     */
    @RequestMapping("/converter4")
    public String converter4(){
        Map<String,Object> book = chatClient.prompt("请给我推荐几本悬疑类型的书").call()
                .entity(new MapOutputConverter());
        return book.toString();
    }

    /*
    *   通过List<Message>实现记忆
    */
    @GetMapping("/memory")
    public String memory() {

        List<Message> messages = new ArrayList<>();

        //第一轮对话
        messages.add(new SystemMessage("你是一个旅行推荐师"));

        messages.add(new UserMessage("我想去新疆玩"));
        messages.add(new AssistantMessage("好的，我知道了，你要去新疆，请问你准备什么时候去"));
        messages.add(new UserMessage("我准备元旦的时候去玩"));
        messages.add(new AssistantMessage("好的，请问你想玩那些内容？"));

        messages.add(new UserMessage("我喜欢自然风光"));

        Prompt prompt = new Prompt(messages);
        return chatModel.call(prompt).getResult().getOutput().getText();
    }

    /*
     *   ChatMemory实现记忆
     */
    @GetMapping("/memory2")
    public String memory2(String message,String chatId) {
        return chatClient.prompt().user(message)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, chatId))
                .call().content();
    }

}

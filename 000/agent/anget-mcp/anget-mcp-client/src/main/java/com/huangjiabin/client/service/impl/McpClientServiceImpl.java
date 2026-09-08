package com.huangjiabin.client.service.impl;

import com.alibaba.fastjson2.JSON;
import com.huangjiabin.client.service.McpClientService;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class McpClientServiceImpl implements McpClientService {

    @Autowired
    private List<McpSyncClient> mcpSyncClientList;  //自动注入的

    @Autowired
    private SyncMcpToolCallbackProvider toolCallbackProvider;   //自动注入的

    @Autowired
    private OpenAiChatModel chatModel;

    private ChatClient chatClient;

    @PostConstruct
    public void init() {
        ToolCallback[] toolCallbacks = toolCallbackProvider.getToolCallbacks();

        this.chatClient = ChatClient.builder(chatModel)
                .defaultToolCallbacks(toolCallbacks)
                .build();

//        this.chatClient = ChatClient.builder(chatModel)
//                .defaultTools(new WeatherService())
//                .build();
    }


    @Override
    public McpSchema.CallToolResult callTool(String type) {
        String toolName = "getWeather";
        Map param = new HashMap();
        param.put("city", "北京");
        for(McpSyncClient mcpSyncClient: mcpSyncClientList){
            McpSchema.Implementation clientInfo = mcpSyncClient.getClientInfo();
            McpSchema.Implementation serverInfo = mcpSyncClient.getServerInfo();
            log.info("clientInfo: {}", JSON.toJSONString(clientInfo));
            log.info("serverInfo: {}", JSON.toJSONString(serverInfo));
            if (clientInfo.title().contains(type)) {
                log.info("mcp服务开始调用");
                McpSchema.CallToolRequest request = McpSchema.CallToolRequest.builder().name(toolName).arguments(param).build();
                McpSchema.CallToolResult result = mcpSyncClient.callTool(request);
                log.info("callTool result: {}", result);
                return result;
            }

        }
        log.info("====================================================");
        return null;
    }

    @Override
    public String chat(String userMessage) {
        return chatClient.prompt().user(userMessage).call().content();
    }
}

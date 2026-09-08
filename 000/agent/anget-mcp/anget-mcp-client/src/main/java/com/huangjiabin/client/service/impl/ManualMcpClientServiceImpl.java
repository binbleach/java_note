package com.huangjiabin.client.service.impl;

import com.huangjiabin.client.callback.ReturnDirectMcpToolCallbackProvider;
import com.huangjiabin.client.service.McpClientService;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.spec.McpSchema;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mcp.McpConnectionInfo;
import org.springframework.ai.mcp.McpToolFilter;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

/*
*   手动创建mcpClient
*/
@Service
public class ManualMcpClientServiceImpl implements McpClientService {

    @Resource
    private ChatModel chatModel;

    private ChatClient chatClient;

    @PostConstruct
    public void init(){
        // stdio
        ServerParameters serverParameters = ServerParameters.builder("java")
                .args("-jar", "D:\\Java\\IDEA_WorkSpece\\java_notes\\000\\agent\\anget-mcp\\anget-mcp-stdio\\target\\anget-mcp-stdio-0.0.1-SNAPSHOT.jar")
                .build();
        StdioClientTransport stdioClientTransport = new StdioClientTransport(serverParameters, McpJsonMapper.createDefault());
        McpSyncClient stdioClient = McpClient.sync(stdioClientTransport)
                .clientInfo(new McpSchema.Implementation("stdio-client", "1.0"))
                .requestTimeout(Duration.ofSeconds(10)).build();
        stdioClient.initialize();

        //sse
        HttpClientSseClientTransport sseClientTransport = HttpClientSseClientTransport.builder("http://127.0.0.1:8081").sseEndpoint("/sse").build();
        McpSyncClient sseClient = McpClient.sync(sseClientTransport).clientInfo(new McpSchema.Implementation("sse-client", "1.0"))
                .requestTimeout(Duration.ofSeconds(10)).build();
        sseClient.initialize();

        //streamable
        HttpClientStreamableHttpTransport streamableTransport = HttpClientStreamableHttpTransport.builder("http://127.0.0.1:8082").endpoint("/mcp").build();
        McpSyncClient streamableClient = McpClient.sync(streamableTransport)
                .clientInfo(new McpSchema.Implementation("streamable-client", "1.0"))
                .requestTimeout(Duration.ofSeconds(10))
                .build();
        streamableClient.initialize();

        List<McpSyncClient> clientList = List.of(stdioClient,sseClient,streamableClient);

        SyncMcpToolCallbackProvider mcpToolCallbackProvider = SyncMcpToolCallbackProvider.builder()
                .mcpClients(clientList)
                .toolFilter((cone, tool) -> tool.name().startsWith("goods"))
                .build();
        ToolCallback[] toolCallbacks = mcpToolCallbackProvider.getToolCallbacks();

        this.chatClient = ChatClient.builder(chatModel).defaultToolCallbacks(toolCallbacks).build();

        // 关闭mcp调用的二次总结
//        ReturnDirectMcpToolCallbackProvider returnDirectMcpToolCallbackProvider = new ReturnDirectMcpToolCallbackProvider(clients,true);
//        ToolCallback[] callbacks = returnDirectMcpToolCallbackProvider.getToolCallbacks();
//
//        this.chatClient = ChatClient.builder(chatModel).defaultToolCallbacks(callbacks).build();

    }


    @Override
    public McpSchema.CallToolResult callTool(String type) {
        return null;
    }

    @Override
    public String chat(String userMessage) {
        return this.chatClient.prompt().user(userMessage).call().content();
    }
}

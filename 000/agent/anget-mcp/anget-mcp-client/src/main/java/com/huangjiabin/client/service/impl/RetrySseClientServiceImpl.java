package com.huangjiabin.client.service.impl;

import com.huangjiabin.client.service.McpClientService;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/*
    sse的断点重连改造
*/
@Service
@Slf4j
public class RetrySseClientServiceImpl implements McpClientService {

    @Autowired
    private ChatModel chatModel;

    private ChatClient chatClient;

    private McpSyncClient sseClient;

    // 是否正在重试 initialize（保证唯一性）
    private final AtomicBoolean retrying = new AtomicBoolean(false);

    // initialize 重试线程
    private final ExecutorService retryExecutor = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void init() {
        log.info("初始化 sseClient...");
        startRetryInitialize();
    }

    private McpSyncClient buildClient(){
        HttpClientSseClientTransport transport = HttpClientSseClientTransport
                .builder("http://127.0.0.1:8081")
                .sseEndpoint("/sse")
                .build();

        return McpClient.sync(transport)
                .clientInfo(new io.modelcontextprotocol.spec.McpSchema.Implementation("sse-client", "1.0"))
                .requestTimeout(Duration.ofSeconds(10))
                .build();
    }

    /**
     * 定时任务：每 5 秒 ping 一次 SSE
     * ping 不通则触发 initialize 重试线程
     */
    @Scheduled(fixedDelay = 5000)
    public void pingSse() {
        log.info("开始ping sseClient====================");
        if (sseClient == null) {
            log.info("sseClient 初始化失败====================");
            startRetryInitialize();
            return;
        }
        try {
            sseClient.ping();
            log.info("sseClient ping 成功========================");
        } catch (Exception e) {
            log.error("sseClient ping 失败========================: {}", e.getMessage());
            startRetryInitialize();
        }
    }

    /**
     * 启动 initialize 重试线程
     */
    private void startRetryInitialize(){
        // 保证只启动一个重试线程
        if(!retrying.compareAndSet(false,true)){
            log.info("已有SSE重建任务执行中，本次跳过");
            return;
        }
        retryExecutor.submit(()->{
            log.info("重新初始化sseClient====================");
            try {
                while (true) {
                    try {
                        // 重建 sseClient
                        this.sseClient = buildClient();
                        this.sseClient.initialize();
                        log.info("sseClient初始化成功====================");

                        // chatClient 也同样需要重建
                        SyncMcpToolCallbackProvider provider = SyncMcpToolCallbackProvider.builder()
                                .mcpClients(List.of(this.sseClient))
                                .build();

                        ToolCallback[] callbacks = provider.getToolCallbacks();

                        this.chatClient = ChatClient.builder(chatModel)
                                .defaultToolCallbacks(callbacks)
                                .defaultTools()
                                .build();
                        return;
                    } catch (Exception e) {
                        log.warn("初始化失败, 十秒后重试. Reason: {}", e.getMessage());
                    }
                    // 把sleep放到外层大try，统一捕获中断
                    Thread.sleep(10000);
                }
            } catch (InterruptedException e) {
                log.warn("重建线程中断，退出");
                Thread.currentThread().interrupt();
            } finally {
                // 无论成功、异常、Error、中断，统一释放锁
                retrying.set(false);
                log.info("重建流程结束，重置重试标记");
            }
        });
    }


    @Override
    public McpSchema.CallToolResult callTool(String type) {
        return null;
    }

    @Override
    public String chat(String userMessage) {
        return chatClient.prompt()
                .user(userMessage)
                .call()
                .content();
    }
}

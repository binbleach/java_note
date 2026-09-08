package com.huangjiabin.client.service;

import io.modelcontextprotocol.spec.McpSchema;

public interface McpClientService {

    /**
     * 直接调用mcp server
     */
    McpSchema.CallToolResult callTool(String type);

    /*
    *   通过会话调用
    */
    String chat(String userMessage);
}

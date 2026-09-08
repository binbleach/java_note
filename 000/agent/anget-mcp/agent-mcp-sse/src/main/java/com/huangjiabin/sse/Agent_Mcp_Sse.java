package com.huangjiabin.sse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*
1、客户端发起 SSE 订阅（建立长连接，获取 sessionId）
    访问：http://127.0.0.1:8081/sse
    返回：event:endpoint
        data:/mcp/messages?sessionId=763ac4f0-f139-4438-aed8-a5c2ca45c326
2、客户端发送initialize请求（post请求）：
    访问：http://127.0.0.1:8081/mcp/messages?sessionId=763ac4f0-f139-4438-aed8-a5c2ca45c326
    请求体：
        {
          "jsonrpc": "2.0",
          "id": 1,
          "method": "initialize",
          "params": {
            "protocolVersion": "0.1.0",
            "capabilities": {},
            "clientInfo": {"name": "mcp-sse-client","version": "1.0.0"}
          }
        }
    返回：状态码200
    第一步长连接返回：event:message
        data:{"jsonrpc":"2.0","id":1,"result":{"protocolVersion":"2024-11-05","capabilities":{"logging":{},"tools":{"listChanged":true}},"serverInfo":{"name":"mcp-server","version":"1.0.0"}}}
3、客户端发送 notifications/initialized 通知（很多简易自研 MCP-SSE 服务做了兼容：不强制校验 initialized 通知，跳过也能正常对话）
    请求体：
        {
          "jsonrpc": "2.0",
          "method": "notifications/initialized"
        }
    返回：状态码200
    第一步长连接返回：event:message
        data:{"jsonrpc":"2.0","id":2,"result":{"tools":[{"name":"getWeather","description":"根据城市名称查询天气信息","inputSchema":{"type":"object","properties":{"city":{"type":"string"}},"required":["city"],"additionalProperties":false}}]}}
4、握手完成后，再拉取工具列表
    请求体：
        {
          "jsonrpc": "2.0",
          "id": 2,
          "method": "tools/list",
          "params": {}
        }
    返回：状态码200
    第一步长连接返回和第三步一样
5、客户端工具调用：
    请求体：
        {
          "jsonrpc": "2.0",
          "id": 3,
          "method": "tools/call",
          "params": {
            "name": "getWeather",
            "arguments": {"city":"Beijing"}
          }
        }
    返回：状态码200
    第一步长连接返回：event:message
        data:{"jsonrpc":"2.0","id":3,"result":{"content":[{"type":"text","text":"\"Beijing: 下雪, -20°C\""}],"isError":false}}
*/
@SpringBootApplication
public class Agent_Mcp_Sse {
    public static void main(String[] args) {
        SpringApplication.run(Agent_Mcp_Sse.class,args);
    }
}

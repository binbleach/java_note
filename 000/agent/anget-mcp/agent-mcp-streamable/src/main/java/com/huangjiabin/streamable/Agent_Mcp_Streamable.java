package com.huangjiabin.streamable;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
1、客户端发送initialize请求（post请求）：
    访问：http://127.0.0.1:8082/mcp
    请求头：Accept:text/event-stream,application/json
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
    返回：{"jsonrpc":"2.0","id":1,"result":{"protocolVersion":"2025-06-18","capabilities":{"completions":{},"logging":{},"prompts":{"listChanged":true},"resources":{"subscribe":false,"listChanged":true},"tools":{"listChanged":true}},"serverInfo":{"name":"mcp-streamable-server","version":"1.0.0"},"instructions":"这个服务是用来查询天气的。"}}
    响应头：Mcp-Session-Id:915ac4d8-6e83-45cf-b8c0-93b5d681876c
2、拉取工具列表：
    请求头：Accept:text/event-stream,application/json；Mcp-Session-Id:915ac4d8-6e83-45cf-b8c0-93b5d681876c
    请求体：
        {
          "jsonrpc": "2.0",
          "id": 2,
          "method": "tools/list"
        }
    返回：id:915ac4d8-6e83-45cf-b8c0-93b5d681876c event:message data:{"jsonrpc":"2.0","id":2,"result":{"tools":[{"name":"query_weather_by_city&date","description":"根据城市和日期获取天气信息","inputSchema":{"type":"object","properties":{"request":{"type":"object","properties":{"city":{"type":"string","description":"城市"},"date":{"type":"string","description":"日期"},"i":{"type":"string","description":"区县"},"s":{"type":"string","description":"街道"}},"required":["city","date","i","s"]}},"required":["request"],"additionalProperties":false}},{"name":"getWeather","description":"根据城市名称查询天气信息","inputSchema":{"type":"object","properties":{"city":{"type":"string"}},"required":["city"],"additionalProperties":false}}]}}
3、工具调用：
    请求体：
        {
          "jsonrpc": "2.0",
          "id": 3,
          "method": "tools/call",
          "params": {
            "name": "getWeather",
            "arguments": {"city":"北京"}
          }
        }
    返回：id:915ac4d8-6e83-45cf-b8c0-93b5d681876c event:message data:{"jsonrpc":"2.0","id":3,"result":{"content":[{"type":"text","text":"\"北京: 晴, 25°C\""}],"isError":false}}

4、mcp-client调用：
{
    “mcpServers": {
        "weather-streamable": {
            "url": "http://127.0.0.1:8082/mcp",
            "type": "streamableHttp",
            "timeout": 60,
            "disabled": false
        }
    }
}

*/
@SpringBootApplication
public class Agent_Mcp_Streamable {

    public static void main(String[] args) {
        SpringApplication.run(Agent_Mcp_Streamable.class, args);
    }
}

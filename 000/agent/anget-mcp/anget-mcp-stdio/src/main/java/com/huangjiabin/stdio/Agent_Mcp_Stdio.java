package com.huangjiabin.stdio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*
    1、要打成jar
    2、mcp-client调用：
    {
        "mcpServers": {
            "weather-stdio": {
                "url": "http://127.0.0.1:8083/mcp",
                "type": "stdio",
                "timeout": 60,
                "disabled": false,
                "command": "java",
                "args": [
                    "-jar",
                    "D:\Java\IDEA_WorkSpece\java_notes\000\agent\anget-mcp\anget-mcp-stdio\target\anget-mcp-stdio-0.0.1-SNAPSHOT.jar"
                ]
            }
        }
    }
*/
@SpringBootApplication
public class Agent_Mcp_Stdio {
    public static void main(String[] args) {
        SpringApplication.run(Agent_Mcp_Stdio.class,args);
    }
}

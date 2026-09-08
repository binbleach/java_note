package com.huangjiabin.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
*   该连接需要确保stdio的jar位置，确保sse和streamable服务都启动
*/
@SpringBootApplication
@EnableScheduling
public class Agent_Mcp_Client {
    public static void main(String[] args) {
        SpringApplication.run(Agent_Mcp_Client.class, args);
    }
}

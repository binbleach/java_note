package com.huangjiabin.client.controller;


import com.huangjiabin.client.service.McpClientService;
import com.huangjiabin.client.service.impl.ManualMcpClientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/client")
public class McpClientController {
    @Qualifier("mcpClientServiceImpl")
    @Autowired
    private McpClientService mcpClientService;

    @Qualifier("manualMcpClientServiceImpl")
    @Autowired
    private McpClientService manualMcpClientService;

    @Qualifier("retrySseClientServiceImpl")
    @Autowired
    private McpClientService retrySseClientService;

    @GetMapping("/callTool")
    public Object callTool(@RequestParam("type") String type) {
        return mcpClientService.callTool(type);
    }

    @GetMapping("/chat")
    public String chat(String query) {
        log.info("chat request => {}", query);
        return mcpClientService.chat(query);
    }

    @GetMapping("/manualChat")
    public String manualChat(@RequestParam("query") String query) {
        log.info("manualChat request => {}", query);

        return manualMcpClientService.chat(query);
    }

    @GetMapping("/retryChat")
    public String retry(@RequestParam("query") String query) {
        log.info("retry chat request => {}", query);

        return retrySseClientService.chat(query);
    }

}

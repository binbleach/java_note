package com.bin.springai.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/*
*   java里的流式调用
*/
@RestController
@RequestMapping("/stream")
public class StreamController {

    @RequestMapping("/sse")
    public SseEmitter sse(){
        // 设置超时时间，0代表永不超时，单位毫秒，默认30秒会自动断开
        SseEmitter emitter = new SseEmitter(60_000L);

        // 异步线程池，单独执行推送逻辑，不占用Tomcat线程
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                for (int i = 0; i < 1000; i++) {
                    // 构建标准SSE消息：自动拼接 data:xxx\n\n
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .data("Message " + i)
                            .id(String.valueOf(i)) // 消息ID，断线重连用
                            .name("msg"); // 自定义事件名，前端可监听
                    emitter.send(event);
                    Thread.sleep(500);
                }
                // 正常推送完成，关闭流
                emitter.complete();
            } catch (IOException | InterruptedException e) {
                // 异常关闭
                emitter.completeWithError(e);
            } finally {
                emitter.complete();
            }
        });
        // 客户端主动断开连接的回调（释放资源）
        emitter.onCompletion(() -> System.out.println("SSE连接正常关闭"));
        emitter.onError((e) -> System.out.println("SSE异常断开：" + e.getMessage()));
        emitter.onTimeout(() -> System.out.println("SSE连接超时"));

        return emitter;
    }

    @GetMapping("entity")
    public Callable<ResponseEntity<StreamingResponseBody>> chat(){
        return () -> {
            StreamingResponseBody body = outputStream -> {
                try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
                    for (int i = 0; i < 1000; i++) {
                        String sseMsg = "data: Message " + i + "\n\n";
                        writer.write(sseMsg);
                        writer.flush();
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_EVENT_STREAM_VALUE)
                    .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                    .header(HttpHeaders.CONNECTION, "keep-alive")
                    .body(body);
        };
    }

    @GetMapping("/flux")
    public Flux<ServerSentEvent<String>> fluxStream(){
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> ServerSentEvent.<String>builder()
                .data("Message " + seq)
                .build());
    }
}

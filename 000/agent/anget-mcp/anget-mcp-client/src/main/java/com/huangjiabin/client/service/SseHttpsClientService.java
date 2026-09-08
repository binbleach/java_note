package com.huangjiabin.client.service;


import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.Duration;

public class SseHttpsClientService {

    public static void createInsecureHttpsClient(String baseUrl, String endpoint) {
        try {
            // 1. 创建一个信任所有证书的 TrustManager
            TrustManager[] trustManagers = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}

                        @Override
                        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            // 2. 初始化 SSL 上下文，绕过校验
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, new SecureRandom());

            // 3. 创建并配置 SSLParameters 以禁用主机名验证
            SSLParameters sslParameters = new SSLParameters();
            sslParameters.setEndpointIdentificationAlgorithm(null);

            // 4、将ssl上下文和参数设置进httpClient
            HttpClient.Builder httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(30))
                    .sslContext(sslContext)
                    .sslParameters(sslParameters);

            // 5、创建httpRequest，并添加token用于校验
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl))
                    .header("Authorization", "Bearer abc123456789");

            // 6、将httpClient 和 httpRequest传进 sseTransport
            HttpClientSseClientTransport transport = HttpClientSseClientTransport.builder(baseUrl).sseEndpoint(endpoint)
                    .clientBuilder(httpClient)
                    .requestBuilder(requestBuilder)
                    .build();
            // 7、构建mcpClient
            McpSyncClient mcpClient = McpClient.sync(transport).build();
            // 8、mcp 初始化
            mcpClient.initialize();
            System.out.println("MCP Client 初始化成功");
        } catch (Exception e) {
            throw new RuntimeException("创建 Insecure MCP Client 失败", e);
        }
    }


    public static void createSecureHttpsClient(String baseUrl, String endpoint, String caCertPath){
        try {
            // 1. 加载 CA 证书
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            FileInputStream fileInputStream = new FileInputStream(caCertPath);
            Certificate caCert = certificateFactory.generateCertificate(fileInputStream);
            fileInputStream.close();

            // 2. 创建 KeyStore ，导入 CA
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("caCert",caCert);

            // 3. 构建 TrustManagerFactory。并导入 KeyStore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            // 4. 创建 SSLContext，并导入 trustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

            // 5. 使用默认 Hostname 验证
            HttpClient.Builder httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(30))
                    .sslContext(sslContext);

            // 6. 构建 SSE Transport
            HttpClientSseClientTransport transport = HttpClientSseClientTransport.builder(baseUrl)
                    .sseEndpoint(endpoint)
                    .clientBuilder(httpClient)
                    .requestBuilder(HttpRequest.newBuilder().header("Authorization", "Bearer abc123456789"))
                    .build();

            // 7. 初始化 MCP Client
            McpSyncClient mcp = McpClient.sync(transport).build();
            mcp.initialize();
            System.out.println("MCP Client 初始化成功");

        } catch (Exception e) {
            throw new RuntimeException("创建 Insecure MCP Client 失败", e);
        }

    }
    public static void creatNotSSLHttpsClient(String baseUrl, String endpoint){
        // 1. 构建 SSE Transport
        HttpClientSseClientTransport transport = HttpClientSseClientTransport.builder(baseUrl)
                .sseEndpoint(endpoint)
                .build();

        // 2. 初始化 MCP Client
        McpSyncClient mcp = McpClient.sync(transport).build();
        mcp.initialize();
        System.out.println("MCP Client 初始化成功");
    }

    /*
        https校验流程：
        1、客户端访问时会先拿到服务器实时发来的站点证书；
        2、客户端沿着证书链向上追溯，用本地预装的根证书逐级验签；
            1）验签通过 → 证书可信，建立加密连接；
            2）找不到信任根 / 签名失效 → 报证书不安全。
        3、浏览器作为客户端的化可以强制访问，
            java作为客户端的要自己绕过ssl的校验如 createInsecureHttpsClient，否则直接访问会报错如 creatNotSSLHttpsClient
    */
    public static void main(String[] args) {


        // mcp服务加了上下文的时候调用（绕过校验版）
        createInsecureHttpsClient("https://127.0.0.1:8081/test/", "sse");
        // 正常无上下文时调用（绕过校验版）
        createInsecureHttpsClient("https://127.0.0.1:8081", "/sse");
        // 校验证书版
//        createSecureHttpsClient("https://127.0.0.1:8081","/sse",
//"D:\\Java\\IDEA_WorkSpece\\java_notes\\000\\agent\\anget-mcp\\agent-mcp-sse\\src\\main\\resources\\sse-server.cer");
        //直接访问不做任何处理版，报错：unable to find valid certification path to requested target
//        creatNotSSLHttpsClient("https://127.0.0.1:8081", "/sse");
    }

}

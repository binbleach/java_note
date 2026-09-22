package com.huangjiabin.document.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.huangjiabin.document.constant.ContentType;
import com.huangjiabin.document.constant.DocumentStatus;
import com.huangjiabin.document.entity.KnowledgeDocument;
import com.huangjiabin.document.service.FileProcessService;
import com.huangjiabin.document.service.FileStorageService;
import com.huangjiabin.document.service.KnowledgeDocumentService;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.huangjiabin.document.constant.ContentType.ZIP;

/**
 * 文件处理服务 - 负责文档转换处理
 */
@Slf4j
public abstract class MinerUProcessBaseServiceImpl implements FileProcessService {

    private static final String CONVERTED_FILE_DIR = "converted/";

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private KnowledgeDocumentService knowledgeDocumentService;

    @Value("${file.parse.api.url:http://localhost:8000}")
    private String fileParseApiUrl;

    @Value("${file.parse.api.connectTimeout:30000}")
    private int connectTimeout;

    @Value("${file.parse.api.responseTimeout:300000}")
    private int responseTimeout;

    /** V1 API 轮询解析任务的间隔（毫秒） */
    @Value("${file.parse.api.pollInterval:2000}")
    private long pollIntervalMs;

    /** V1 API 轮询解析任务的总超时（毫秒），超时后任务仍在服务端执行，可凭 job_id 继续轮询 */
    @Value("${file.parse.api.pollTimeout:300000}")
    private long pollTimeoutMs;

    /**
     * 处理文档转换 - Markdown 格式
     * 1. 从 MinIO 下载文件
     * 2. 调用文档解析接口获取md/zip
     * 3. 转换后的文档保存在minio上
     * 3. 更新文档状态和转换后的 URL
     *
     * @param document 文档对象
     */
    public void processDocument(KnowledgeDocument document, InputStream inputStream) {
        processDocumentToMarkdownFromZip(document, inputStream);
    }

    /**
     * 处理文档转换为 Markdown 格式
     *
     * @param document 文档对象
     */
    public void processDocumentToMarkdown(KnowledgeDocument document, InputStream inputStream) {
        log.info("开始处理文档转换为 Markdown，documentId: {}", document.getDocTitle());

        // 更新状态为转换中
        document.setStatus(DocumentStatus.CONVERTING);
        boolean result = knowledgeDocumentService.updateById(document);
        Assert.isTrue(result, "文件CONVERTING状态更新失败");

        try {
            // 生成一串数字，避免文件名的中文乱码
            String docTitle = document.getDocTitle() + document.getDocTitle().hashCode();

            // 调用文档解析获取 Markdown
            String parseResult = parseDocumentToMarkdown(docTitle, inputStream);

            String markdownContent = JSON.parseObject(parseResult).getJSONObject("results").getJSONObject(docTitle).getString("md_content");
            // 保存转换后的内容到 MinIO
            String convertedObjectName = CONVERTED_FILE_DIR + document.getDocTitle().substring(0, document.getDocTitle().lastIndexOf(".")) + ".md";
            String convertedUrl = fileStorageService.uploadFile(convertedObjectName, markdownContent.getBytes(), ContentType.TEXT_MARKDOWN);

            // 更新文档状态为已转换
            document.setStatus(DocumentStatus.CONVERTED);
            document.setConvertedDocUrl(convertedUrl);
            result = knowledgeDocumentService.updateById(document);
            Assert.isTrue(result, "文件CONVERTED状态更新失败");
            log.info("文档 Markdown 转换完成，documentId: {}", document.getDocTitle());
        } catch (Exception e) {
            log.error("文档 Markdown 转换失败，documentId: {}", document.getDocTitle(), e);
            // 转换失败，状态回滚为 UPLOADED
            document.setStatus(DocumentStatus.UPLOADED);
            result = knowledgeDocumentService.updateById(document);
            Assert.isTrue(result, "文件UPLOADED状态更新失败");
            throw new RuntimeException("文档 Markdown 转换失败: " + e.getMessage(), e);
        } finally {
            closeQuietly(inputStream);
        }
    }


    /**
     * 处理文档转换为 ZIP 格式，不处理图片
     * 1. 从 MinIO 下载文件
     * 2. 调用文档解析接口获取 ZIP（包含 Markdown 和图片）
     * 3. 更新文档状态和转换后的 URL
     *
     * @param document 文档对象
     */
    public void processDocumentToZip(KnowledgeDocument document, InputStream inputStream) {
        log.info("开始处理文档转换为 ZIP，documentId: {}", document.getDocTitle());

        // 更新状态为转换中
        document.setStatus(DocumentStatus.CONVERTING);
        boolean result = knowledgeDocumentService.updateById(document);
        Assert.isTrue(result, "文件CONVERTING状态更新失败");

        try {
            // 生成一串数字，避免文件名的中文乱码
            String docTitle = document.getDocTitle() + document.getDocTitle().hashCode();

            // 调用文档解析获取 ZIP 格式响应
            byte[] zipBytes = parseDocumentToZip(docTitle, inputStream);

            // 保存转换后的 ZIP 到 MinIO
            String convertedObjectName = CONVERTED_FILE_DIR + document.getDocTitle().substring(0, document.getDocTitle().lastIndexOf(".")) + ".zip";
            String convertedUrl = fileStorageService.uploadFile(convertedObjectName, zipBytes, ZIP);

            // 更新文档状态为已转换
            document.setStatus(DocumentStatus.CONVERTED);
            document.setConvertedDocUrl(convertedUrl);
            result = knowledgeDocumentService.updateById(document);
            Assert.isTrue(result, "文件CONVERTED状态更新失败");

            log.info("文档 ZIP 转换完成，documentId: {}", document.getDocTitle());
        } catch (Exception e) {
            log.error("文档 ZIP 转换失败，documentId: {}", document.getDocTitle(), e);
            // 转换失败，状态回滚为 UPLOADED
            document.setStatus(DocumentStatus.UPLOADED);
            result = knowledgeDocumentService.updateById(document);
            Assert.isTrue(result, "文件UPLOADED状态更新失败");
            throw new RuntimeException("文档 ZIP 转换失败: " + e.getMessage(), e);
        } finally {
            closeQuietly(inputStream);
        }
    }

    /**
     * 处理文档转换为 ZIP 格式，处理图片
     * 1. 调用文档解析接口获取 ZIP（包含 Markdown 和图片）
     * 2. 保存 ZIP 到本地磁盘
     * 3. 解压 ZIP 文件：md + 图片
     * 4. 上传解压后的图片到 MinIO
     * 5，调用 LLM 生成图片描述
     * 6. 替换 md 中的图片地址为 MinIO 地址，并加上图片描述
     * 7. 上传 md 到 MinIO
     * 7. 保存 md 的 MinIO 地址到 convertedUrl（mysql字段更新）
     * 8. 异步清理本地临时文件
     *
     * @param document 文档对象
     */
    public void processDocumentToMarkdownFromZip(KnowledgeDocument document, InputStream inputStream) {
        log.info("开始处理文档转换为 ZIP，documentId: {}", document.getDocTitle());

        // 更新状态为转换中
        document.setStatus(DocumentStatus.CONVERTING);
        boolean result = knowledgeDocumentService.updateById(document);
        Assert.isTrue(result, "文件CONVERTING状态更新失败");

        String zipFilePath = null;
        String extractDir = null;

        try {
            // V1 API 的文件名通过 JSON（UTF-8）传输不会乱码，直接使用原始文件名（需保留扩展名用于文件类型识别）
            String docTitle = document.getDocTitle();

            // 1. 调用文档解析获取 ZIP 格式响应（V1 API）
            byte[] zipBytes = parseDocumentToZipV1(docTitle, inputStream); //本地部署

            // 2. 保存 ZIP 到本地临时目录
            String tempDir = System.getProperty("java.io.tmpdir");
            String uniqueId = UUID.randomUUID().toString();
            zipFilePath = tempDir + File.separator + uniqueId + ".zip";
            extractDir = tempDir + File.separator + uniqueId + "_extracted";

            Files.write(Paths.get(zipFilePath), zipBytes);
            log.info("ZIP 文件已保存到本地: {}", zipFilePath);

            // 3. 解压 ZIP 文件
            extractZip(zipFilePath, extractDir);
            log.info("ZIP 文件已解压到: {}", extractDir);

            // 4. 上传解压后的 md 和图片到 MinIO，并处理 md 内容
            String mdMinioUrl = processExtractedFiles(document, extractDir);

            // 5. 更新文档状态为已转换，保存 md 的 MinIO 地址
            document.setStatus(DocumentStatus.CONVERTED);
            document.setConvertedDocUrl(mdMinioUrl);
            result = knowledgeDocumentService.updateById(document);
            Assert.isTrue(result, "文件CONVERTED状态更新失败");

            log.info("文档 ZIP 转换完成，documentId: {}, mdUrl: {}", document.getDocTitle(), mdMinioUrl);
        } catch (Exception e) {
            log.error("文档 ZIP 转换失败，documentId: {}", document.getDocTitle(), e);
            // 转换失败，状态回滚为 UPLOADED
            document.setStatus(DocumentStatus.UPLOADED);
            result = knowledgeDocumentService.updateById(document);
            Assert.isTrue(result, "文件UPLOADED状态更新失败");
            throw new RuntimeException("文档 ZIP 转换失败: " + e.getMessage(), e);
        } finally {
            closeQuietly(inputStream);
            // 异步清理临时文件
            cleanupTempFilesAsync(zipFilePath, extractDir);
        }
    }

    /**
     * 解压 ZIP 文件到指定目录
     */
    private void extractZip(String zipFilePath, String extractDir) throws IOException {
        Path extractPath = Paths.get(extractDir);
        Files.createDirectories(extractPath);

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path entryPath = extractPath.resolve(entry.getName());

                // 安全检查：防止 ZIP 路径遍历攻击
                if (!entryPath.normalize().startsWith(extractPath.normalize())) {
                    log.warn("跳过不安全的 ZIP 条目: {}", entry.getName());
                    continue;
                }

                if (entry.isDirectory()) {
                    Files.createDirectories(entryPath);
                } else {
                    Files.createDirectories(entryPath.getParent());
                    Files.copy(zis, entryPath, StandardCopyOption.REPLACE_EXISTING);
                }
                zis.closeEntry();
            }
        }
    }

    /**
     * 处理解压后的文件：上传 md 和图片到 MinIO，替换图片地址，生成图片描述
     */
    private String processExtractedFiles(KnowledgeDocument document, String extractDir) throws Exception {
        Path extractPath = Paths.get(extractDir);

        // 查找所有的 md 文件和图片文件
        Path mdFile = null;
        java.util.List<Path> imageFiles = new java.util.ArrayList<>();

        try (Stream<Path> paths = Files.walk(extractPath)) {
            for (Path path : paths.toList()) {
                if (Files.isRegularFile(path)) {
                    String fileName = path.getFileName().toString().toLowerCase();
                    if (fileName.endsWith(".md")) {
                        mdFile = path;
                    } else if (fileName.endsWith(".png") || fileName.endsWith(".jpg") ||
                            fileName.endsWith(".jpeg") || fileName.endsWith(".gif") ||
                            fileName.endsWith(".webp") || fileName.endsWith(".bmp")) {
                        imageFiles.add(path);
                    }
                }
            }
        }

        if (mdFile == null) {
            throw new RuntimeException("解压后的文件夹中未找到 Markdown 文件");
        }

        log.info("找到 Markdown 文件: {}, 图片文件数量: {}", mdFile, imageFiles.size());

        // 上传图片到 MinIO，并建立本地文件名到 MinIO URL 的映射
        java.util.Map<String, String> imageUrlMap = new java.util.HashMap<>();
        String baseObjectName = CONVERTED_FILE_DIR + document.getDocTitle() + "/";

        for (Path imagePath : imageFiles) {
            String imageName = imagePath.getFileName().toString();
            byte[] imageBytes = Files.readAllBytes(imagePath);
            String contentType = getImageContentType(imageName);
            String objectName = baseObjectName + "images/" + imageName;
            String imageUrl = fileStorageService.uploadFile(objectName, imageBytes, contentType);
            imageUrlMap.put(imageName, imageUrl);
            log.info("图片已上传到 MinIO: {} -> {}", imageName, imageUrl);
        }

        // 读取 md 文件内容
        String mdContent = Files.readString(mdFile, StandardCharsets.UTF_8);

        // 替换 md 中的图片地址为 MinIO 地址，并生成图片描述
        String processedMdContent = processMarkdownImages(mdContent, imageUrlMap);

        // 上传处理后的 md 文件到 MinIO
        String mdObjectName = baseObjectName + mdFile.getFileName().toString();
        String mdUrl = fileStorageService.uploadFile(mdObjectName, processedMdContent.getBytes(StandardCharsets.UTF_8), ContentType.TEXT_MARKDOWN);
        log.info("Markdown 文件已上传到 MinIO: {}", mdUrl);

        return mdUrl;
    }

    /**
     * 获取图片的 Content-Type
     */
    private String getImageContentType(String fileName) {
        String lowerName = fileName.toLowerCase();
        if (lowerName.endsWith(".png")) return "image/png";
        if (lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")) return "image/jpeg";
        if (lowerName.endsWith(".gif")) return "image/gif";
        if (lowerName.endsWith(".webp")) return "image/webp";
        if (lowerName.endsWith(".bmp")) return "image/bmp";
        return "application/octet-stream";
    }

    /**
     * 处理 Markdown 中的图片标签：替换地址并生成图片描述
     * 匹配格式: ![](xxx.png) 或 ![alt](xxx.png)
     */
    private String processMarkdownImages(String mdContent, java.util.Map<String, String> imageUrlMap) {
        // 匹配图片标签的正则表达式: ![alt](path)
        Pattern pattern = Pattern.compile("!\\[(.*?)\\]\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(mdContent);

        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String altText = matcher.group(1);
            String imagePath = matcher.group(2);

            // 提取图片文件名
            String imageName = Paths.get(imagePath).getFileName().toString();

            // 获取 MinIO 上的图片 URL
            String minioUrl = imageUrlMap.get(imageName);
            if (minioUrl == null) {
                // 如果找不到对应的 MinIO URL，保持原样
                log.warn("未找到图片 {} 对应的 MinIO URL", imageName);
                matcher.appendReplacement(result, Matcher.quoteReplacement(matcher.group(0)));
                continue;
            }

            // 生成图片描述（mock 实现）
            String imageDescription = generateImageDescription(minioUrl);

            // 构建新的图片标签: ![描述](minio_url)
            String newImageTag = "![" + imageDescription + "](" + minioUrl + ")";
            matcher.appendReplacement(result, Matcher.quoteReplacement(newImageTag));

            log.info("图片标签已处理: {} -> {}", imagePath, minioUrl);
        }
        matcher.appendTail(result);

        return result.toString();
    }

    @Value("${langchain4j.open-ai.chat-model.api-key}")
    private String chatModelApiKey;

    @Value("${langchain4j.open-ai.chat-model.base-url}")
    private String chatModelBaseUrl;

    /**
     * 生成图片描述
     * 需要注意的是，如果你用的是外部的模型，这个url需要是公网可以访问的url。否则模型需要能和MinIO进行内网通信。
     */
    public String generateImageDescription(String imageUrl) {
        OpenAiChatModel chatModel = OpenAiChatModel.builder()
                .apiKey(chatModelApiKey)
                .baseUrl(chatModelBaseUrl)
                .modelName("text-embedding-v4")
                .temperature(0.7)
                .logResponses(true)
                .logRequests(true)
                .build();

        UserMessage userMessage = UserMessage.from(new TextContent("请描述这张图片的内容，包括场景、对象、布局、颜色、文字信息，直接输出纯文本描述，不要多余说明，不要增加任何特殊符号，特别是换行符"), new ImageContent(imageUrl));
        return chatModel.chat(userMessage).aiMessage().text();
    }

    /**
     * 异步清理临时文件
     */
    private void cleanupTempFilesAsync(String zipFilePath, String extractDir) {
        if (zipFilePath == null && extractDir == null) {
            return;
        }

        Thread.startVirtualThread(() -> {
            try {
                // 删除 ZIP 文件
                if (zipFilePath != null) {
                    Files.deleteIfExists(Paths.get(zipFilePath));
                    log.info("临时 ZIP 文件已删除: {}", zipFilePath);
                }

                // 删除解压目录
                if (extractDir != null) {
                    deleteDirectory(Paths.get(extractDir));
                    log.info("临时解压目录已删除: {}", extractDir);
                }
            } catch (Exception e) {
                log.warn("清理临时文件失败", e);
            }
        });
    }

    /**
     * 递归删除目录
     */
    private void deleteDirectory(Path directory) throws IOException {
        if (!Files.exists(directory)) {
            return;
        }

        try (Stream<Path> paths = Files.walk(directory)) {
            paths.sorted((a, b) -> -a.compareTo(b)) // 反向排序，先删除子文件/目录
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            log.warn("删除文件失败: {}", path, e);
                        }
                    });
        }
    }

    /**
     * 调用 MinerU 文档解析接口，返回markdown，没有图片
     * 使用 Apache HttpClient 5 替代 HttpURLConnection，提供更好的超时控制和连接管理
     *
     * @param fileName   文件名
     * @param fileStream 文件输入流
     * @return 解析结果
     */
    private String parseDocumentToMarkdown(String fileName, InputStream fileStream) {
        String url = fileParseApiUrl + "/file_parse";

        // 配置请求超时
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Timeout.ofMilliseconds(connectTimeout)).setResponseTimeout(Timeout.ofMilliseconds(responseTimeout)).build();

        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept", "application/json");

            // 构建 multipart 请求体
            HttpEntity multipartEntity = MultipartEntityBuilder.create().addBinaryBody(
                    "files", fileStream, org.apache.hc.core5.http.ContentType.APPLICATION_OCTET_STREAM, fileName
                    )
                    .addTextBody("backend", "pipeline")
                    .addTextBody("response_format_zip", "false")
                    .addTextBody("return_images", "false").
                    addTextBody("return_model_output", "false").
                    addTextBody("return_middle_json", "false")
                    .build();

            httpPost.setEntity(multipartEntity);

            log.info("开始调用文件解析接口: {}", url);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getCode();
                log.info("文件解析接口响应状态码: {}", statusCode);

                HttpEntity responseEntity = response.getEntity();
                String responseBody = responseEntity != null ? EntityUtils.toString(responseEntity, "UTF-8") : "";

                if (statusCode == 200) {
                    log.info("文件解析接口调用成功，响应体长度: {}", responseBody.length());
                    return responseBody;
                } else {
                    log.error("文件解析接口调用失败，状态码: {}, 响应: {}", statusCode, responseBody);
                    throw new RuntimeException("文件解析接口调用失败: HTTP " + statusCode + ", " + responseBody);
                }
            }
        } catch (Exception e) {
            log.error("调用文件解析接口异常", e);
            throw new RuntimeException("调用文件解析接口失败: " + e.getMessage(), e);
        } finally {
            closeQuietly(fileStream);
        }
    }

    /**
     * 调用 MinerU 文档解析接口，返回zip，有图片
     * 使用 Apache HttpClient 5，支持流式下载大文件
     *
     * @param fileName   文件名
     * @param fileStream 文件输入流
     * @return ZIP 文件字节数组
     */
    private byte[] parseDocumentToZip(String fileName, InputStream fileStream) {
        String url = fileParseApiUrl + "/file_parse";


        // 配置请求超时
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Timeout.ofMilliseconds(connectTimeout)).setResponseTimeout(Timeout.ofMilliseconds(responseTimeout)).build();

        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept", "application/json");

            // 构建 multipart 请求体，启用 ZIP 格式和返回图片
            HttpEntity multipartEntity = MultipartEntityBuilder.create()
                    .addBinaryBody("files", fileStream, org.apache.hc.core5.http.ContentType.APPLICATION_OCTET_STREAM, fileName)
                    .addTextBody("backend", "pipeline").addTextBody("response_format_zip", "true")
                    .addTextBody("return_images", "true").addTextBody("return_model_output", "false")
                    .addTextBody("return_middle_json", "false").build();

            httpPost.setEntity(multipartEntity);

            log.info("开始调用文件解析接口（ZIP 模式）: {}", url);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getCode();
                log.info("文件解析接口响应状态码: {}", statusCode);

                HttpEntity responseEntity = response.getEntity();
                if (statusCode == 200 && responseEntity != null) {
                    // 读取响应体为字节数组（ZIP 文件）
                    byte[] zipBytes = EntityUtils.toByteArray(responseEntity);
                    log.info("文件解析接口调用成功，ZIP 文件大小: {} bytes", zipBytes.length);
                    return zipBytes;
                } else {
                    String responseBody = responseEntity != null ? EntityUtils.toString(responseEntity, "UTF-8") : "";
                    log.error("文件解析接口调用失败，状态码: {}, 响应: {}", statusCode, responseBody);
                    throw new RuntimeException("文件解析接口调用失败: HTTP " + statusCode + ", " + responseBody);
                }
            }

        } catch (Exception e) {
            log.error("调用文件解析接口异常", e);
            throw new RuntimeException("调用文件解析接口失败: " + e.getMessage(), e);
        } finally {
            closeQuietly(fileStream);
        }
    }

    /**
     * 调用 MinerU V1 API（4.0+ 新版接口），返回 ZIP（包含 Markdown 和图片）
     * 旧版 /file_parse 接口已在 MinerU 4.0 移除，新流程为：
     * 创建上传会话 -> 上传字节 -> 完成上传 -> 创建解析任务 -> 轮询任务终态 -> 下载 ZIP 产物
     * 参考: https://opendatalab.github.io/MinerU/zh/usage/http_api/
     *
     * @param fileName   文件名（需保留扩展名，用于文件类型识别）
     * @param fileStream 文件输入流
     * @return ZIP 文件字节数组
     */
    private byte[] parseDocumentToZipV1(String fileName, InputStream fileStream) {
        log.info("开始调用文件解析接口（V1 ZIP 模式）: {}", fileParseApiUrl);

        try (CloseableHttpClient httpClient = createV1HttpClient()) {
            // 创建上传会话需要提前告知文件大小，先读取全部字节
            byte[] fileBytes = fileStream.readAllBytes();

            // 1. 创建上传会话
            JSONObject upload = createUploadSession(httpClient, fileName, fileBytes);

            // 2. 上传字节并完成上传，得到 file_id（命中秒传时跳过上传两步）
            String fileId = uploadFileAndGetFileId(httpClient, upload, fileBytes);

            // 3. 创建解析任务，输出格式为 zip
            String jobId = createParseJob(httpClient, fileId);

            // 4. 轮询任务状态直到终态
            JSONObject job = pollParseJob(httpClient, jobId);

            // 5. 提取 zip 产物并下载
            String zipFileId = extractZipFileId(job);
            byte[] zipBytes = downloadFileContent(httpClient, zipFileId);
            log.info("文件解析接口调用成功，ZIP 文件大小: {} bytes", zipBytes.length);
            return zipBytes;
        } catch (Exception e) {
            log.error("调用文件解析接口异常", e);
            throw new RuntimeException("调用文件解析接口失败: " + e.getMessage(), e);
        } finally {
            closeQuietly(fileStream);
        }
    }

    /**
     * 创建 V1 HTTP 客户端，复用统一超时配置
     */
    private CloseableHttpClient createV1HttpClient() {
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Timeout.ofMilliseconds(connectTimeout)).setResponseTimeout(Timeout.ofMilliseconds(responseTimeout)).build();
        return HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * 创建上传会话: POST /v1/uploads
     */
    private JSONObject createUploadSession(CloseableHttpClient httpClient, String fileName, byte[] fileBytes) throws Exception {
        String url = fileParseApiUrl + "/v1/uploads";

        JSONObject body = new JSONObject();
        body.put("filename", fileName);
        body.put("bytes", fileBytes.length);
        body.put("mime_type", guessMimeType(fileName));
        body.put("purpose", "parse");

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(body.toString(), org.apache.hc.core5.http.ContentType.APPLICATION_JSON));

        JSONObject response = JSON.parseObject(executeExpectingOk(httpClient, httpPost, url));
        Assert.notNull(response, "创建上传会话失败：响应为空");
        return response;
    }

    /**
     * 上传原始字节并完成上传，返回 file_id
     * 创建上传响应状态为 completed 且携带 file 对象时为秒传，跳过字节上传和完成两步
     */
    private String uploadFileAndGetFileId(CloseableHttpClient httpClient, JSONObject upload, byte[] fileBytes) throws Exception {
        // 秒传：文件已存在，直接返回 file_id
        JSONObject file = upload.getJSONObject("file");
        if (file != null && "completed".equals(upload.getString("status"))) {
            log.info("文件秒传命中，跳过字节上传: {}", file.getString("id"));
            return file.getString("id");
        }

        String uploadId = upload.getString("id");
        Assert.hasText(uploadId, "创建上传会话失败：响应中缺少 upload_id");

        // 上传原始字节：按响应返回的 upload_url 执行（相对路径基于 API 基地址解析）
        String uploadUrl = resolveApiUrl(upload.getString("upload_url"));
        Assert.notNull(uploadUrl, "创建上传会话失败：响应中缺少 upload_url");
        HttpPut httpPut = new HttpPut(uploadUrl);
        httpPut.setEntity(new ByteArrayEntity(fileBytes, org.apache.hc.core5.http.ContentType.APPLICATION_OCTET_STREAM));

        // 响应返回的上传请求头原样附加（本地匿名部署无需鉴权头）
        JSONObject uploadHeaders = upload.getJSONObject("upload_headers");
        if (uploadHeaders != null) {
            for (Map.Entry<String, Object> entry : uploadHeaders.entrySet()) {
                httpPut.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        executeExpectingOk(httpClient, httpPut, uploadUrl);
        log.info("文件字节上传完成: {}", uploadId);

        // 完成上传: POST /v1/uploads/{upload_id}/complete
        String completeUrl = fileParseApiUrl + "/v1/uploads/" + uploadId + "/complete";
        HttpPost completePost = new HttpPost(completeUrl);
        JSONObject completed = JSON.parseObject(executeExpectingOk(httpClient, completePost, completeUrl));
        JSONObject fileObject = completed != null ? completed.getJSONObject("file") : null;
        Assert.notNull(fileObject, "完成上传失败：响应中缺少 file 对象");
        return fileObject.getString("id");
    }

    /**
     * 创建解析任务: POST /v1/parse/jobs
     * tier=basic 对应旧版 backend=pipeline（本地轻量模型流程），输出格式为 zip
     */
    private String createParseJob(CloseableHttpClient httpClient, String fileId) throws Exception {
        String url = fileParseApiUrl + "/v1/parse/jobs";

        JSONObject source = new JSONObject();
        source.put("type", "file_id");
        source.put("file_id", fileId);
        JSONObject fileEntry = new JSONObject();
        fileEntry.put("source", source);

        JSONObject body = new JSONObject();
        body.put("files", List.of(fileEntry));
        body.put("tier", "basic");
        body.put("output_formats", List.of("zip"));

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(body.toString(), org.apache.hc.core5.http.ContentType.APPLICATION_JSON));

        JSONObject response = JSON.parseObject(executeExpectingOk(httpClient, httpPost, url));
        String jobId = response != null ? response.getString("job_id") : null;
        Assert.hasText(jobId, "创建解析任务失败：响应中缺少 job_id");
        log.info("解析任务已创建: {}", jobId);
        return jobId;
    }

    /**
     * 轮询解析任务状态: GET /v1/parse/jobs/{job_id}
     * 终态: completed、partial、failed、canceled；轮询超时后任务仍在服务端执行，可凭 job_id 继续轮询
     */
    private JSONObject pollParseJob(CloseableHttpClient httpClient, String jobId) throws Exception {
        String url = fileParseApiUrl + "/v1/parse/jobs/" + jobId;
        long deadline = System.currentTimeMillis() + pollTimeoutMs;

        while (true) {
            HttpGet httpGet = new HttpGet(url);
            JSONObject job = JSON.parseObject(executeExpectingOk(httpClient, httpGet, url));
            Assert.notNull(job, "查询解析任务失败：响应为空");
            String status = job.getString("status");
            log.info("解析任务状态: {} -> {}", jobId, status);

            if ("completed".equals(status) || "partial".equals(status)) {
                return job;
            }
            if ("failed".equals(status) || "canceled".equals(status)) {
                throw new RuntimeException("解析任务异常终态: " + status + ", " + job);
            }

            if (System.currentTimeMillis() + pollIntervalMs > deadline) {
                throw new RuntimeException("轮询解析任务超时，任务未取消，可凭 job_id 继续轮询: " + jobId);
            }
            Thread.sleep(pollIntervalMs);
        }
    }

    /**
     * 从任务结果中提取 zip 产物的 file_id
     */
    private String extractZipFileId(JSONObject job) {
        Assert.notEmpty(job.getJSONArray("files"), "解析任务结果中缺少文件信息");
        JSONObject file = job.getJSONArray("files").getJSONObject(0);

        if (!"completed".equals(file.getString("status"))) {
            JSONObject error = file.getJSONObject("error");
            throw new RuntimeException("文件解析失败: " + (error != null ? error.getString("message") : file.getString("status")));
        }

        JSONObject outputFiles = file.getJSONObject("output_files");
        JSONObject zip = outputFiles != null ? outputFiles.getJSONObject("zip") : null;
        Assert.notNull(zip, "解析结果中缺少 zip 产物");
        return zip.getString("file_id");
    }

    /**
     * 下载产物内容: GET /v1/files/{file_id}/content
     * 可能返回 302 重定向，HttpClient 默认自动跟随
     */
    private byte[] downloadFileContent(CloseableHttpClient httpClient, String fileId) throws Exception {
        String url = fileParseApiUrl + "/v1/files/" + fileId + "/content";
        HttpGet httpGet = new HttpGet(url);

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            int statusCode = response.getCode();
            HttpEntity responseEntity = response.getEntity();
            if (statusCode == 200 && responseEntity != null) {
                return EntityUtils.toByteArray(responseEntity);
            }
            String responseBody = responseEntity != null ? EntityUtils.toString(responseEntity, "UTF-8") : "";
            log.error("下载解析产物失败，状态码: {}, 响应: {}", statusCode, responseBody);
            throw new RuntimeException("下载解析产物失败: HTTP " + statusCode + ", " + responseBody);
        }
    }

    /**
     * 执行请求并校验响应为 2xx，返回响应体文本
     */
    private String executeExpectingOk(CloseableHttpClient httpClient, HttpUriRequestBase request, String url) throws Exception {
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getCode();
            HttpEntity responseEntity = response.getEntity();
            String responseBody = responseEntity != null ? EntityUtils.toString(responseEntity, "UTF-8") : "";
            if (statusCode >= 200 && statusCode < 300) {
                return responseBody;
            }
            log.error("接口调用失败，url: {}, 状态码: {}, 响应: {}", url, statusCode, responseBody);
            throw new RuntimeException("接口调用失败: HTTP " + statusCode + ", " + responseBody);
        }
    }

    /**
     * 解析服务端返回的接口地址：相对路径基于 API 基地址解析
     */
    private String resolveApiUrl(String url) {
        if (url == null) {
            return null;
        }
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        }
        String base = fileParseApiUrl.endsWith("/") ? fileParseApiUrl.substring(0, fileParseApiUrl.length() - 1) : fileParseApiUrl;
        return base + (url.startsWith("/") ? url : "/" + url);
    }

    /**
     * 根据文件名推断 MIME 类型
     */
    private String guessMimeType(String fileName) {
        String lowerName = fileName.toLowerCase();
        if (lowerName.endsWith(".pdf")) return "application/pdf";
        if (lowerName.endsWith(".doc")) return "application/msword";
        if (lowerName.endsWith(".docx")) return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        if (lowerName.endsWith(".ppt")) return "application/vnd.ms-powerpoint";
        if (lowerName.endsWith(".pptx")) return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        if (lowerName.endsWith(".html")) return "text/html";
        if (lowerName.endsWith(".png")) return "image/png";
        if (lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")) return "image/jpeg";
        return "application/octet-stream";
    }

    /**
     * 安静关闭输入流，忽略异常
     *
     * @param inputStream 输入流
     */
    private void closeQuietly(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Exception ignored) {
                // 忽略关闭异常
            }
        }
    }
}


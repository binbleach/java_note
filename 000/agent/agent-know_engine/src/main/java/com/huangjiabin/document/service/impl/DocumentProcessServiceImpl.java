package com.huangjiabin.document.service.impl;

import com.huangjiabin.document.constant.DocumentStatus;
import com.huangjiabin.document.constant.KnowledgeBaseType;
import com.huangjiabin.document.entity.DocumentUploadParam;
import com.huangjiabin.document.entity.KnowledgeDocument;
import com.huangjiabin.document.service.*;
import com.huangjiabin.document.util.FileTypeUtil;
import com.huangjiabin.infra.lock.DistributeLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;


/*
    文档处理服务实现类
    负责文档的业务流程处理：上传、转换、分段、向量化
*/
@Slf4j
@Service
public class DocumentProcessServiceImpl implements DocumentProcessService {

    @Autowired
    private KnowledgeDocumentService knowledgeDocumentService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileProcessServiceFactory fileProcessServiceFactory;

    @Override
    @DistributeLock(scene = "document-upload", keyExpression = "#documentUploadParam.uploadUser", waitTime = 0) //分布式锁注解
    public KnowledgeDocument upload(DocumentUploadParam documentUploadParam) throws IOException {
        try {
            log.info("start to upload ....");
            String fileName = documentUploadParam.file().getOriginalFilename();
            // 上传文档到 Minio
            String fileUrl = fileStorageService.uploadFile(documentUploadParam.file(), fileName);

            // 构建文档记录
            KnowledgeDocument document = new KnowledgeDocument();
            document.setDocTitle(documentUploadParam.title());
            document.setUploadUser(documentUploadParam.uploadUser());
            document.setDocUrl(fileUrl);
            document.setStatus(DocumentStatus.UPLOADED);
            document.setAccessibleBy(documentUploadParam.accessibleBy());
            document.setDescription(documentUploadParam.description());
            document.setKnowledgeBaseType(KnowledgeBaseType.valueOf(documentUploadParam.knowledgeBaseType()));
            document.setTableName(documentUploadParam.tableName());

            // 保存记录到数据库
            boolean result = knowledgeDocumentService.save(document);
            Assert.isTrue(result, "文件上传失败");

            //根据文档类型选择不同策略处理
            FileProcessService fileProcessService = fileProcessServiceFactory.get(FileTypeUtil.getFileType(fileName, documentUploadParam.file()), document.getKnowledgeBaseType());
            if (fileProcessService != null) {
                fileProcessService.processDocument(document, documentUploadParam.file().getInputStream());
            } else {
                if (document.getKnowledgeBaseType() == KnowledgeBaseType.DOCUMENT_SEARCH) {
                    document.setStatus(DocumentStatus.CONVERTED);
                    document.setConvertedDocUrl(fileUrl);
                    result = knowledgeDocumentService.updateById(document);
                    Assert.isTrue(result, "文件状态更新失败");
                } else {
                    document.setStatus(DocumentStatus.STORED);
                    document.setConvertedDocUrl(fileUrl);
                    result = knowledgeDocumentService.updateById(document);
                    Assert.isTrue(result, "文件状态更新失败");
                }
            }
            return document;
        } catch (Exception e) {
            throw new IOException("文件上传失败: " + e.getMessage(), e);
        }
    }
}

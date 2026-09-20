package com.huangjiabin.document.service;

import com.huangjiabin.document.entity.DocumentUploadParam;
import com.huangjiabin.document.entity.KnowledgeDocument;

import java.io.IOException;

public interface DocumentProcessService {

    KnowledgeDocument upload(DocumentUploadParam documentUploadParam) throws IOException;
}

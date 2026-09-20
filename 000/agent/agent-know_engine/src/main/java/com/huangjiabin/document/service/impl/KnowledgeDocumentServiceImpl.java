package com.huangjiabin.document.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huangjiabin.document.entity.KnowledgeDocument;
import com.huangjiabin.document.mapper.KnowledgeDocumentMapper;
import com.huangjiabin.document.service.KnowledgeDocumentService;
import org.springframework.stereotype.Service;

/**
 * 知识文档表 Service 实现类
 */
@Service
public class KnowledgeDocumentServiceImpl extends ServiceImpl<KnowledgeDocumentMapper, KnowledgeDocument> implements KnowledgeDocumentService {

}

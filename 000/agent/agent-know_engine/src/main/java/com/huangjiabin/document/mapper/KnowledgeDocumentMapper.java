package com.huangjiabin.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huangjiabin.document.entity.KnowledgeDocument;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识文档表 Mapper 接口
 */
@Mapper
public interface KnowledgeDocumentMapper extends BaseMapper<KnowledgeDocument> {
}

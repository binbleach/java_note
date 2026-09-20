package com.huangjiabin.document.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huangjiabin.document.entity.KnowledgeSegment;

import java.io.Serializable;

/**
 * 知识片段表 Service 接口
 */
public interface KnowledgeSegmentService extends IService<KnowledgeSegment> {

    public String getTextByChunkId(Serializable chunkId);
}

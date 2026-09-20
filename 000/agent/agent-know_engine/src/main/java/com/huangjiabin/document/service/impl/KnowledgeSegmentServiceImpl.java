package com.huangjiabin.document.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huangjiabin.document.entity.KnowledgeSegment;
import com.huangjiabin.document.mapper.KnowledgeSegmentMapper;
import com.huangjiabin.document.service.KnowledgeSegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 知识片段表 Service 实现类
 */
@Service
public class KnowledgeSegmentServiceImpl extends ServiceImpl<KnowledgeSegmentMapper, KnowledgeSegment> implements KnowledgeSegmentService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String getTextByChunkId(Serializable chunkId) {
        String text = stringRedisTemplate.opsForValue().get(chunkId);
        if (text != null) {
            if (text.isEmpty()) {
                return null;
            }
            return text;
        }

        QueryWrapper<KnowledgeSegment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chunk_id", chunkId);
        KnowledgeSegment segment = super.getOne(queryWrapper);

        if (segment != null) {
            stringRedisTemplate.opsForValue().set(chunkId.toString(), segment.getText(), 30, TimeUnit.SECONDS);
            return segment.getText();
        } else {
            // 缓存空值，避免缓存击穿，重复查询数据库
            stringRedisTemplate.opsForValue().set(chunkId.toString(), "");
        }

        return null;
    }
}

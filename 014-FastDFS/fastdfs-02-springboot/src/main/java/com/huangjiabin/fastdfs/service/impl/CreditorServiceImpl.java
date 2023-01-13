package com.huangjiabin.fastdfs.service.impl;

import com.huangjiabin.fastdfs.mapper.CreditorFileMapper;
import com.huangjiabin.fastdfs.mapper.CreditorInfoMapper;
import com.huangjiabin.fastdfs.model.CreditorFile;
import com.huangjiabin.fastdfs.model.CreditorInfo;
import com.huangjiabin.fastdfs.service.CreditorService;
import com.huangjiabin.fastdfs.util.FastDFSUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CreditorServiceImpl implements CreditorService {
    @Resource
    private CreditorInfoMapper creditorInfoMapper;
    @Resource
    private CreditorFileMapper creditorFileMapper;
    @Override
    public List<CreditorInfo> getCreditorList() {
        return creditorInfoMapper.selectAll();
    }

    @Override
    public void addCreditorFile(CreditorFile creditorFile) {
        creditorFileMapper.insert(creditorFile);
    }

    @Override
    public void deleteFileById(Integer creditorFileId) {
        CreditorFile creditorFile=creditorFileMapper.selectByPrimaryKey(creditorFileId);
        FastDFSUtil.delete(creditorFile.getGroupName(),creditorFile.getRemoteFileName());
        creditorFileMapper.deleteByPrimaryKey(creditorFileId);
    }

    @Override
    public CreditorFile getCreditorFileById(Integer creditorFileId) {
        CreditorFile creditorFile=creditorFileMapper.selectByPrimaryKey(creditorFileId);
        return creditorFile;
    }
}

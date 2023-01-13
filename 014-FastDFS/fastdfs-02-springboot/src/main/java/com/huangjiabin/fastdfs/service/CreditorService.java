package com.huangjiabin.fastdfs.service;

import com.huangjiabin.fastdfs.model.CreditorFile;
import com.huangjiabin.fastdfs.model.CreditorInfo;

import java.util.List;

public interface CreditorService {

    List<CreditorInfo> getCreditorList();

    void addCreditorFile(CreditorFile creditorFile);

    void deleteFileById(Integer creditorFileId);

    CreditorFile getCreditorFileById(Integer creditorFileId);
}
package com.huangjiabin.document.service;

import com.huangjiabin.document.constant.FileType;
import com.huangjiabin.document.constant.KnowledgeBaseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileProcessServiceFactory {

    @Autowired
    private List<FileProcessService> fileProcessServiceList;

    public FileProcessService get(FileType fileProcessType, KnowledgeBaseType knowledgeBaseType) {
        return fileProcessServiceList.stream()
                .filter(service -> service.supports(fileProcessType, knowledgeBaseType))
                .findFirst().orElse(null);
    }
}

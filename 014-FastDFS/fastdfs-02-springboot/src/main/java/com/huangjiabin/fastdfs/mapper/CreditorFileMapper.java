package com.huangjiabin.fastdfs.mapper;

import com.huangjiabin.fastdfs.model.CreditorFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CreditorFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CreditorFile record);

    int insertSelective(CreditorFile record);

    CreditorFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CreditorFile record);

    int updateByPrimaryKey(CreditorFile record);
}
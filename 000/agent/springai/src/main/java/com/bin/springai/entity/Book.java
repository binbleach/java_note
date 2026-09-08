package com.bin.springai.entity;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

// record类的新写法
public record Book(
        @JsonPropertyDescription("序号，唯一")
        Long id,
        @JsonPropertyDescription("书名，以中文名展示")
        String name,
        @JsonPropertyDescription("作者")
        String author,
        @JsonPropertyDescription("类型")
        String category,
        @JsonPropertyDescription("评分")
        Integer score,
        @JsonPropertyDescription("简介")
        String intro
) {
}

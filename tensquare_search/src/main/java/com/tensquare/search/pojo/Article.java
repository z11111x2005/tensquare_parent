package com.tensquare.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

@Document(indexName = "tensquare_article", type = "article")
public class Article implements Serializable {

    @Id
    private String id;

    // 是否索引,就是看该域是否能被搜索。
    // 是否分词,就表示搜索的时候是整体的匹配还是按照单词匹配
    // 是否存储,就是是否在页面上显示
    // index = true是否使用分词器,analyzer指定存储时使用的分词器,searchAnalyzer指定字段搜索时使用的分词器
    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;// 标题

    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;// 内容

    private String state;// 审核状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

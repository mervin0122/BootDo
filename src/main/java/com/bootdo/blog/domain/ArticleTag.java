package com.bootdo.blog.domain;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 文章标签实体
 * @author eumji
 * @package com.bootdo.blog.domain
 * @name ArticleTag.java
 * @date 2017/4/12
 * @time 12:34
 */
@Alias("articleTag")
public class ArticleTag implements Serializable {

private String articleId;  //文章id

private String tagId;  //标签id

private String tagName;  //标签名

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "ArticleTag{" +
                "articleId=" + articleId +
                ", tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}

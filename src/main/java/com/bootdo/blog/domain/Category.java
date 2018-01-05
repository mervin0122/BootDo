package com.bootdo.blog.domain;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 分类实体
 * @author eumji
 * @package com.bootdo.blog.domain
 * @name Category.java
 * @date 2017/4/12
 * @time 12:34
 */
@Alias("category")
public class Category implements Serializable {

    private Integer id;

    private String categoryName; //分类名称

    private String aliasName;  //分类别名

    private Integer sort;

    private Integer status;  //状态

    private Date createTime;    //创建时间


    public Integer getId() {
return id;
}

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", aliasName='" + aliasName + '\'' +
                ", sort=" + sort +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}

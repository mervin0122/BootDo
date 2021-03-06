package com.bootdo.blog.domain;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 友链实体类
 * @author eumji
 * @package com.bootdo.blog.domain
 * @name Pager.java
 * @date 2017/4/11
 * @time 12:32
 */
@Alias("partner")
public class Partner implements Serializable {


    private Integer id;

    private String siteName;    //友链名称

    private String siteUrl; //友链URL

    private String siteDesc; //友链描述

    private Integer sort;  //排序

    private Integer status;  //状态

    private Date createTime;    //创建时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getSiteDesc() {
        return siteDesc;
    }

    public void setSiteDesc(String siteDesc) {
        this.siteDesc = siteDesc;
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
        return "Partner{" +
                "id=" + id +
                ", siteName='" + siteName + '\'' +
                ", siteUrl='" + siteUrl + '\'' +
                ", siteDesc='" + siteDesc + '\'' +
                ", sort=" + sort +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}

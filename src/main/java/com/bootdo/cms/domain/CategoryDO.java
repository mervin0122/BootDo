package com.bootdo.cms.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author zscat
 * @email 951449465@qq.com
 * @date 2017-12-02 18:07:28
 */
public class CategoryDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//描述
	private String description;
	//图片
	private String image;
	//关键字
	private String keywords;
	//模块
	private String module;
	//名称
	private String name;
	//
	private Long parentId;
	//级别
	private Integer grade;
	//是否展示
	private Integer isshow;
	//
	private Integer orderno;
	//网址
	private Long siteId;
	//链接
	private String url;
	//
	private String parentIds;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：图片
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取：图片
	 */
	public String getImage() {
		return image;
	}
	/**
	 * 设置：关键字
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	/**
	 * 获取：关键字
	 */
	public String getKeywords() {
		return keywords;
	}
	/**
	 * 设置：模块
	 */
	public void setModule(String module) {
		this.module = module;
	}
	/**
	 * 获取：模块
	 */
	public String getModule() {
		return module;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * 设置：级别
	 */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	/**
	 * 获取：级别
	 */
	public Integer getGrade() {
		return grade;
	}
	/**
	 * 设置：是否展示
	 */
	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}
	/**
	 * 获取：是否展示
	 */
	public Integer getIsshow() {
		return isshow;
	}
	/**
	 * 设置：
	 */
	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}
	/**
	 * 获取：
	 */
	public Integer getOrderno() {
		return orderno;
	}
	/**
	 * 设置：网址
	 */
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	/**
	 * 获取：网址
	 */
	public Long getSiteId() {
		return siteId;
	}
	/**
	 * 设置：链接
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：链接
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：
	 */
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	/**
	 * 获取：
	 */
	public String getParentIds() {
		return parentIds;
	}
}

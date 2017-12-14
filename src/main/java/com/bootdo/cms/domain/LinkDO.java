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
public class LinkDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//颜色
	private String color;
	//链接
	private String href;
	//图片
	private String image;
	//标题
	private String title;
	//权重
	private Integer weight;
	//
	private Date weightdate;
	//
	private Long categoryId;

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
	 * 设置：颜色
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * 获取：颜色
	 */
	public String getColor() {
		return color;
	}
	/**
	 * 设置：链接
	 */
	public void setHref(String href) {
		this.href = href;
	}
	/**
	 * 获取：链接
	 */
	public String getHref() {
		return href;
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
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：权重
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	/**
	 * 获取：权重
	 */
	public Integer getWeight() {
		return weight;
	}
	/**
	 * 设置：
	 */
	public void setWeightdate(Date weightdate) {
		this.weightdate = weightdate;
	}
	/**
	 * 获取：
	 */
	public Date getWeightdate() {
		return weightdate;
	}
	/**
	 * 设置：
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取：
	 */
	public Long getCategoryId() {
		return categoryId;
	}
}

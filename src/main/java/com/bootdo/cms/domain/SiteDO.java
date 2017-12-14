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
public class SiteDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//版权
	private String copyright;
	//描述
	private String description;
	//关键字
	private String keywords;
	//logo
	private String logo;
	//名称
	private String name;
	//主题
	private String theme;
	//标题
	private String title;

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
	 * 设置：版权
	 */
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	/**
	 * 获取：版权
	 */
	public String getCopyright() {
		return copyright;
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
	 * 设置：logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * 获取：logo
	 */
	public String getLogo() {
		return logo;
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
	 * 设置：主题
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}
	/**
	 * 获取：主题
	 */
	public String getTheme() {
		return theme;
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
}

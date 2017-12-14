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
public class CommentDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//
	private Date auditdate;
	//
	private String content;
	//
	private Date createdate;
	//
	private Integer delflag;
	//
	private String email;
	//
	private String ip;
	//
	private String name;
	//
	private String url;
	//
	private Long articleId;
	//
	private Long auditerId;
	//
	private String headpath;

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
	 * 设置：
	 */
	public void setAuditdate(Date auditdate) {
		this.auditdate = auditdate;
	}
	/**
	 * 获取：
	 */
	public Date getAuditdate() {
		return auditdate;
	}
	/**
	 * 设置：
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	/**
	 * 获取：
	 */
	public Date getCreatedate() {
		return createdate;
	}
	/**
	 * 设置：
	 */
	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}
	/**
	 * 获取：
	 */
	public Integer getDelflag() {
		return delflag;
	}
	/**
	 * 设置：
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取：
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * 设置：
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：
	 */
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	/**
	 * 获取：
	 */
	public Long getArticleId() {
		return articleId;
	}
	/**
	 * 设置：
	 */
	public void setAuditerId(Long auditerId) {
		this.auditerId = auditerId;
	}
	/**
	 * 获取：
	 */
	public Long getAuditerId() {
		return auditerId;
	}
	/**
	 * 设置：
	 */
	public void setHeadpath(String headpath) {
		this.headpath = headpath;
	}
	/**
	 * 获取：
	 */
	public String getHeadpath() {
		return headpath;
	}
}

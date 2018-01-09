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
public class CmsArticleDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//颜色 文章  视频
	private String model;
	//内容
	private String content;
	//
	private Date createdate;
	private String img1;
	private String img2;

	//描述
	private String remark;
	//点击量
	private Integer hits;
	//htmlId
	private String htmlid;
	//图片
	private String img;
	//
	private String keywords;
	//标题
	private String title;
	//权重
	private Integer weight;
	//
	private Long typeid;
	//
	private Integer delflag;
	//
	private Long createby;
	//
	private String moreimage;
	//
	private String typename;
	//
	private Long siteid;
	//分类名称
	private String cname;
	//站点名称
	private String sname;

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}


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
	 * 设置：颜色 文章  视频
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * 获取：颜色 文章  视频
	 */
	public String getModel() {
		return model;
	}
	/**
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：内容
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
	 * 设置：描述
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：描述
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：点击量
	 */
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	/**
	 * 获取：点击量
	 */
	public Integer getHits() {
		return hits;
	}
	/**
	 * 设置：htmlId
	 */
	public void setHtmlid(String htmlid) {
		this.htmlid = htmlid;
	}
	/**
	 * 获取：htmlId
	 */
	public String getHtmlid() {
		return htmlid;
	}
	/**
	 * 设置：图片
	 */
	public void setImg(String img) {
		this.img = img;
	}
	/**
	 * 获取：图片
	 */
	public String getImg() {
		return img;
	}
	/**
	 * 设置：
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	/**
	 * 获取：
	 */
	public String getKeywords() {
		return keywords;
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
	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}
	/**
	 * 获取：
	 */
	public Long getTypeid() {
		return typeid;
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
	public void setCreateby(Long createby) {
		this.createby = createby;
	}
	/**
	 * 获取：
	 */
	public Long getCreateby() {
		return createby;
	}
	/**
	 * 设置：
	 */
	public void setMoreimage(String moreimage) {
		this.moreimage = moreimage;
	}
	/**
	 * 获取：
	 */
	public String getMoreimage() {
		return moreimage;
	}
	/**
	 * 设置：
	 */
	public void setTypename(String typename) {
		this.typename = typename;
	}
	/**
	 * 获取：
	 */
	public String getTypename() {
		return typename;
	}
	/**
	 * 设置：
	 */
	public void setSiteid(Long siteid) {
		this.siteid = siteid;
	}
	/**
	 * 获取：
	 */
	public Long getSiteid() {
		return siteid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}
}

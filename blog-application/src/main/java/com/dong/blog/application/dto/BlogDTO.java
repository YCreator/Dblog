package com.dong.blog.application.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * 博客实体
 * @author Administrator
 *
 */
public class BlogDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1948933168416718722L;
	/**
	 * 
	 */

	private Long id; // 编号
	private String title; // 博客标题
	private String summary; // 摘要
	private Date releaseDate; // 发布日期
 	private Integer clickHit; // 查看次数
	private Integer replyHit; // 回复次数
	private String content; // 博客内容
	private String contentNoTag; // 博客内容 无网页标签 Lucene分词用
	private BlogTypeDTO blogTypeDTO; // 博客类型
	
	private Integer blogCount; // 博客数量 非博客实际属性，主要是 根据发布日期归档查询博客数量用
	private String releaseDateStr; // 发布日期字符串 只取年和月
	private String keyWord; // 关键字 空格隔开
	private String picPath; //博客图片介绍地址
	
	private List<String> imagesList=new LinkedList<String>(); // 博客里存在的图片 主要用于列表展示显示缩略图
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Integer getClickHit() {
		return clickHit;
	}
	public void setClickHit(Integer clickHit) {
		this.clickHit = clickHit;
	}
	public Integer getReplyHit() {
		return replyHit;
	}
	public void setReplyHit(Integer replyHit) {
		this.replyHit = replyHit;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentNoTag() {
		return contentNoTag;
	}
	public void setContentNoTag(String contentNoTag) {
		this.contentNoTag = contentNoTag;
	}
	/**
	 * 使用BeanUtils 复制一个实体到另一个实体时，需要实现get方法,同时方法名要以get+源实体对应的属性名来命名，才能获取到值,例如: blog.blogType值注入对应blogTypeDTO.getBlogType方法
	 * @return
	 */
	public BlogTypeDTO getBlogType() {
		return blogTypeDTO;
	}
	public void setBlogTypeDTO(BlogTypeDTO blogTypeDTO) {
		this.blogTypeDTO = blogTypeDTO;
	}
	public Integer getBlogCount() {
		return blogCount;
	}
	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}
	public String getReleaseDateStr() {
		return releaseDateStr;
	}
	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	public List<String> getImagesList() {
		return imagesList;
	}
	public void setImagesList(List<String> imagesList) {
		this.imagesList = imagesList;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
}

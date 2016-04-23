package com.dong.blog.core.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.dayatang.domain.AbstractEntity;

/**
 * 博客实体
 * @author Administrator
 *
 */
@Entity
@Table(name="t_blog")
public class Blog extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column
	private String title; // 博客标题
	@Column
	private String summary; // 摘要
	@Column
	private Date releaseDate; // 发布日期
	@Column
 	private Integer clickHit; // 查看次数
	@Column
	private Integer replyHit; // 回复次数
	@Column
	private String content; // 博客内容
	@JoinColumn(name="typeId",referencedColumnName="id")//外键  设置对应数据表的列名和引用的数据表的列名
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	private BlogType blogType; // 博客类型
	@Column
	private String keyWord; // 关键字 空格隔开
	@Column
	private String picPath; //博客图片介绍地址

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
	/**
     * ManyToOne：多对一的配置
     * cascade(级联)：all(所有)，merge(更新)，refresh(查询)，persistence(保存)，remove(删除)
     * fetch: eager:立即加载  one的一方默认是立即加载   lazy:懒加载    many的一方默认是懒加载
     * optional:是否可选,外键是否允许为空
     *
     * JoinColumn:指定外键名
     *
     */
	public BlogType getBlogType() {
		return blogType;
	}
	public void setBlogType(BlogType blogType) {
		this.blogType = blogType;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
}

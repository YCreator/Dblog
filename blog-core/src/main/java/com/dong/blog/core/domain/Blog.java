package com.dong.blog.core.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.dong.blog.core.AbstractEntity;

/**
 * 博客实体
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_blog")
@NamedQueries({
		@NamedQuery(name = "Blog.getLastBlog", query = "select new com.dong.blog.core.domain.Blog(_blog.id, _blog.title) from Blog _blog where _blog.id < :id order by _blog.id desc"),
		@NamedQuery(name = "Blog.getNextBlog", query = "select new com.dong.blog.core.domain.Blog(_blog.id, _blog.title) from Blog _blog where _blog.id > :id order by _blog.id asc") })
public class Blog extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false, length = 200)
	private String title; // 博客标题
	@Column(length = 400)
	private String summary; // 摘要
	@Column(name = "release_date", nullable = false)
	private Date releaseDate; // 发布日期
	@Column(name = "click_hit", nullable = false, columnDefinition = "int(11) default 0")
	private Integer clickHit; // 查看次数
	@Column(name = "reply_hit", nullable = false, columnDefinition = "int(11) default 0")
	private Integer replyHit; // 回复次数
	@Column(name = "like_hit", nullable = false, columnDefinition = "int(11) default 0")
	private Integer likeHit; //点赞次数
	@Column(columnDefinition = "text")
	private String content; // 博客内容
	@JoinColumn(name = "type_id", referencedColumnName = "id")
	// 外键 设置对应数据表的列名和引用的数据表的列名
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private BlogType blogType; // 博客类型
	@Column(name = "keyword", length = 200)
	private String keyWord; // 关键字 空格隔开
	@Column(name = "pic_path", length = 120)
	private String picPath; // 博客图片介绍地址

	public Blog() {
	}

	public Blog(Long id, String title) {
		this.setId(id);
		this.setTitle(title);
	}

	public Blog(Long id, String title, Integer replyHit) {
		this.setId(id);
		this.setTitle(title);
		this.setReplyHit(replyHit);
	}

	public Blog(Long id, String title, Date releaseDate, String picPath,
			BlogType blogType) {
		this.setId(id);
		this.setTitle(title);
		this.setReleaseDate(releaseDate);
		this.setPicPath(picPath);
		this.setBlogType(blogType);
	}

	public Blog(Long id, String title, String summary, Date releaseDate,
			Integer clickHit, Integer replyHit, String picPath,
			BlogType blogType) {
		this.setId(id);
		this.setTitle(title);
		this.setSummary(summary);
		this.setReleaseDate(releaseDate);
		this.setClickHit(clickHit);
		this.setReplyHit(replyHit);
		this.setPicPath(picPath);
		this.setBlogType(blogType);
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

	/**
	 * ManyToOne：多对一的配置
	 * cascade(级联)：all(所有)，merge(更新)，refresh(查询)，persistence(保存)，remove(删除)
	 * fetch: eager:立即加载 one的一方默认是立即加载 lazy:懒加载 many的一方默认是懒加载
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
	
	public Integer getLikeHit() {
		return likeHit;
	}

	public void setLikeHit(Integer likeHit) {
		this.likeHit = likeHit;
	}

	@Override
	public String toString() {
		return "Blog [title=" + title + ", summary=" + summary
				+ ", releaseDate=" + releaseDate + ", clickHit=" + clickHit
				+ ", replyHit=" + replyHit + ", likeHit=" + likeHit
				+ ", content=" + content + ", blogType=" + blogType
				+ ", keyWord=" + keyWord + ", picPath=" + picPath + "]";
	}

	public static Blog getNextBlog(Long id) {
		return getRepository().createNamedQuery("Blog.getNextBlog")
				.addParameter("id", id).setMaxResults(1).singleResult();
	}

	public static Blog getLastBlog(Long id) {
		return getRepository().createNamedQuery("Blog.getLastBlog")
				.addParameter("id", id).setMaxResults(1).singleResult();
	}

}

package com.dong.blog.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.dayatang.domain.AbstractEntity;

/**
 * 评论实体
 * @author Administrator
 *
 */
@Entity
@Table(name="t_comment")
public class Comment extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column
	private String userIp; // 用户IP
	@Column
	private String content; // 评论内容
	@Column
	private Long blogId; // 被评论的博客
	@Column
	private Date commentDate; // 评论日期
	@Column
	private Integer state; // 审核状态  0 待审核 1 审核通过 2 审核未通过
	
	
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getBlogId() {
		return blogId;
	}
	public void setBlog(Long blogId) {
		this.blogId = blogId;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	
}

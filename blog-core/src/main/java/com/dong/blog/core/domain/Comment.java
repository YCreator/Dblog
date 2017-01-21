package com.dong.blog.core.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dong.blog.core.AbstractEntity;

/**
 * 评论实体
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_comment")
public class Comment extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "user_ip", length = 50)
	private String userIp; // 用户IP
	@Column(nullable = false, columnDefinition = "text")
	private String content; // 评论内容
	@JoinColumn(name = "blog_id", referencedColumnName = "id")
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private Blog blog; // 被评论的博客
	@Column(name = "comment_date")
	private Date commentDate; // 评论日期
	@Column
	private Integer state; // 审核状态 0 待审核 1 审核通过 2 审核未通过

	public Comment() {
	}

	public Comment(Long id, String userIp, String content, Date commentDate,
			Integer state, Long blogId, String title, Integer replyHit) {
		this.setId(id);
		this.setUserIp(userIp);
		this.setContent(content);
		this.setCommentDate(commentDate);
		this.setState(state);
		this.setBlog(new Blog(blogId, title, replyHit));
	}

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

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
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

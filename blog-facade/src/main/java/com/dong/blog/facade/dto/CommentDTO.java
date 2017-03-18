package com.dong.blog.facade.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论实体
 * @author Administrator
 *
 */
public class CommentDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2807417249713564677L;
	private Long id; // 编号
	private String userIp; // 用户IP
	private String content; // 评论内容
	private BlogDTO blogDTO; // 被评论的博客
	private Date commentDate; // 评论日期
	private Integer state; // 审核状态  0 待审核 1 审核通过 2 审核未通过
	
	public CommentDTO() {}
	
	public CommentDTO(Long id, String userIp, String content, Date commentDate, Integer state, Long blogId, String title, Integer replyHit) {
		this.setId(id);
		this.setUserIp(userIp);
		this.setContent(content);
		this.setCommentDate(commentDate);
		this.setState(state);
		blogDTO = new BlogDTO();
		blogDTO.setId(blogId);
		blogDTO.setTitle(title);
		blogDTO.setReplyHit(replyHit);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public BlogDTO getBlogDTO() {
		return blogDTO;
	}
	public void setBlogDTO(BlogDTO blogDTO) {
		this.blogDTO = blogDTO;
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

	@Override
	public String toString() {
		return "CommentDTO [id=" + id + ", userIp=" + userIp + ", content="
				+ content + ", blogDTO=" + blogDTO + ", commentDate="
				+ commentDate + ", state=" + state + "]";
	}
	
	
	
}

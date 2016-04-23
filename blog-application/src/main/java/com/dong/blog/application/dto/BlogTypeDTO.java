package com.dong.blog.application.dto;

import java.io.Serializable;

/**
 * 博客类型实体
 * @author Administrator
 *
 */
public class BlogTypeDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;  // 编号
	private String typeName; // 博客类型名称
	private Integer blogCount; // 数量
	private Integer orderNo; // 排序  从小到大排序显示
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getBlogCount() {
		return blogCount;
	}
	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	

	
	
}
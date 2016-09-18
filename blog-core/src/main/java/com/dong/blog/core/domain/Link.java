package com.dong.blog.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.dong.blog.core.AbstractEntity;

/**
 * 友情链接实体
 * @author Administrator
 *
 */
@Entity
@Table(name="t_link")
public class Link extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "link_name")
	private String linkName; // 链接名称
	@Column(name = "link_url")
	private String linkUrl; // 链接地址
	@Column(name = "order_no")
	private Integer orderNo; // 排序序号 从小到大排序
	
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	
	
}

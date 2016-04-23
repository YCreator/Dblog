package com.dong.blog.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.dayatang.domain.AbstractEntity;

/**
 * 博客类型实体
 * @author Administrator
 *
 */
@Entity
@Table(name="t_blogtype")
public class BlogType extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column
	private String typeName; // 博客类型名称
	@Column
	private Integer orderNo; // 排序  从小到大排序显示
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	

	
	
}

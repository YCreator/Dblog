package com.dong.blog.core.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.dong.blog.core.AbstractEntity;

/**
 * 博客类型实体
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_blogtype")
@NamedQueries({ @NamedQuery(name = "BlogType.getTotal", query = "select count(*) from BlogType _blogType") })
public class BlogType extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "type_name", nullable = false, length = 30)
	private String typeName; // 博客类型名称
	@Column(name = "order_no")
	private Integer orderNo; // 排序 从小到大排序显示
	@ManyToMany(mappedBy = "blogTypes")
	private Set<Category> categorys = new HashSet<Category>();

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

	public Set<Category> getCategorys() {
		return categorys;
	}

	public void setCategorys(Set<Category> categorys) {
		this.categorys = categorys;
	}

	@Override
	public String toString() {
		return "BlogType [typeName=" + typeName + ", orderNo=" + orderNo;
	}

	public static Long getTotal() {
		return getRepository().createNamedQuery("BlogType.getTotal")
				.singleResult();
	}

}

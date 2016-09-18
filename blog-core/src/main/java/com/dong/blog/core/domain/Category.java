package com.dong.blog.core.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.dong.blog.core.AbstractEntity;

@Entity
@Table(name="t_category")
public class Category extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "cate_name", nullable=false)
	private String categoryName;
	@Column(name = "type_ids", length=80)
	private String typeIds;
	@Column(nullable=false)
	private Integer sort;
	@ManyToMany
	@JoinTable(name="category_blogtype", joinColumns=@JoinColumn(name="cate_id"), inverseJoinColumns=@JoinColumn(name="type_id"))
	private Set<BlogType> blogTypes = new HashSet<BlogType>();
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getTypeIds() {
		return typeIds;
	}
	public void setTypeIds(String typeIds) {
		this.typeIds = typeIds;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Set<BlogType> getBlogTypes() {
		return blogTypes;
	}
	public void setBlogTypes(Set<BlogType> blogTypes) {
		this.blogTypes = blogTypes;
	}
	
	
	

}

package com.dong.blog.core.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.dong.blog.core.AbstractEntity;

@Entity
@Table(name = "t_category")
@NamedQueries({
		@NamedQuery(name = "Category.getTotal", query = "select count(*) from Category _category"),
		@NamedQuery(name = "Category.findAllBySort", query = "select c from Category c order by c.sort asc") })
public class Category extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "cate_name", nullable = false, length = 30)
	private String categoryName;
	@Column(nullable = false)
	private Integer sort;
	@ManyToMany
	@JoinTable(name = "category_blogtype", joinColumns = @JoinColumn(name = "cate_id"), inverseJoinColumns = @JoinColumn(name = "type_id"))
	private Set<BlogType> blogTypes = new HashSet<BlogType>();

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	
	public static Long getTotal() {
		return getRepository().createNamedQuery("Category.getTotal")
				.singleResult();
	}

	public static List<Category> findAllBySort() {
		return getRepository().createNamedQuery("Category.findAllBySort")
				.list();
	}

	@Override
	public String toString() {
		return "Category [categoryName=" + categoryName + ", sort=" + sort
				+ ", blogTypes=" + blogTypes + "]";
	}

}

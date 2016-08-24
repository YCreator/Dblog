package com.dong.blog.application.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String categoryName;
	private String ids;
	private Integer sort;
	
	private List<BlogTypeDTO> list = new ArrayList<BlogTypeDTO>();
	private List<String> blogTypeJsons;
	
	public List<String> getBlogTypeJsons() {
		return blogTypeJsons;
	}
	public void setBlogTypeJsons(List<String> blogTypeJsons) {
		this.blogTypeJsons = blogTypeJsons;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public List<BlogTypeDTO> getList() {
		return list;
	}
	public void setList(List<BlogTypeDTO> list) {
		this.list = list;
	}
	
	@Override
	public String toString() {
		return "CategoryDTO [id=" + id + ", categoryName=" + categoryName
				+ ", ids=" + ids + ", sort=" + sort + ", list=" + blogTypeJsons + "]";
	}
	
	
	

}

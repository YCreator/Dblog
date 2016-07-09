package com.dong.blog.application.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryDTO implements Serializable {
	
	private Long id;
	private String categoryName;
	private String ids;
	private Integer sort;
	
	private List<BlogTypeDTO> list = new ArrayList<BlogTypeDTO>();
	
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
	
	

}

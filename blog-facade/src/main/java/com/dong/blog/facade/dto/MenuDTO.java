package com.dong.blog.facade.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class MenuDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String title;
	private String description;
	private String pageUrl;
	private String icon;
	private Integer sort;
	private Boolean isParent;
	private Date createTime;

	private String menuType;
	private Set<MenuDTO> children = new LinkedHashSet<MenuDTO>();
	
	public MenuDTO() {}
	
	public MenuDTO(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public Set<MenuDTO> getChildren() {
		return children;
	}

	public void setChildren(Set<MenuDTO> children) {
		this.children = children;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "MenuDTO [id=" + id + ", title=" + title + ", description="
				+ description + ", pageUrl=" + pageUrl + ", icon=" + icon
				+ ", sort=" + sort + ", isParent=" + isParent + ", createTime="
				+ createTime + ", menuType=" + menuType + ", children="
				+ children + "]";
	}

	
}

package com.dong.blog.facade.dto;

/**
 * 分页Model类
 * @author 
 *
 */
public class PageBean {

	private int page; // 第几页
	private int pageSize; // 每页记录数
	
	public PageBean(int page, int pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}
	
	public int getPage() {
		return page > 0 ? page - 1 : 0;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		return (page-1)*pageSize;
	}
	
	
}

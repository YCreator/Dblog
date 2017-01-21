package com.dong.blog.application;

import org.dayatang.utils.Page;

import com.dong.blog.core.domain.Link;

public interface LinkApplication extends BaseApplication<Link, Long> {
	
	Page<Link> getPage(int currentPage, int pageSize);
	
	Long getTotal();
	
}
